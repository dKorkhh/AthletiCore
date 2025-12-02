package com.example.athleticore.batch;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.enums.BookingStatus;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ExpiredBookingService {
    private static final Logger logger = LogManager.getLogger(ExpiredBookingService.class);
    private static final Duration GRACE_PERIOD = Duration.ofHours(2);

    private final BookingServiceImpl bookingService;
    private final NotificationServiceImpl notificationService;

    @Transactional
    public List<Long> markExpiredBookings() {
        LocalDateTime threshold = LocalDateTime.now().minus(GRACE_PERIOD);

        List<BookingStatus> activeStatuses = List.of(
                BookingStatus.CONFIRMED,
                BookingStatus.PENDING
        );

        List<Booking> expired = bookingService.findExpiredBookings(activeStatuses, threshold);

        if (expired.isEmpty()) {
            logger.info("No expired bookings found");
            return List.of();
        }

        expired.forEach(b -> b.setBookingStatus(BookingStatus.CANCELLED));
        bookingService.saveAll(expired);

        List<Long> ids = expired.stream().map(Booking::getId).collect(toList());
        logger.info("Marked {} bookings as CANCELED: {}", ids.size(), ids);
        return ids;
    }

    @Transactional(readOnly = true)
    public void notifyUsersAboutCancellation(List<Long> bookingIds) {
        if (bookingIds == null || bookingIds.isEmpty()) {
            logger.info("No bookings to notify about");
            return;
        }

        List<Booking> bookings = bookingService.findAllById(bookingIds);

        bookings.forEach(b -> {
            try {
                notificationService.sendBookingCanceled(b);
            } catch (Exception ex) {
                logger.error("Failed to notify user {} about canceled booking {}",
                        b.getClient().getEmail(), b.getId(), ex);
            }
        });

        logger.info("Notifications sent for {} bookings", bookings.size());
    }
}
