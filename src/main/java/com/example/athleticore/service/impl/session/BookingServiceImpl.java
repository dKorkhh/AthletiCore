package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.entity.Notification;
import com.example.athleticore.service.BookingService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Override
    public void createBooking(BookingDto bookingDto) {
        // save
        sessionService.incrementSessionCount(bookingDto.getSession());
        notificationService.subscribeToNotifications(
                Notification.builder()
                        .client(bookingDto.getClient())
                        .session(bookingDto.getSession()).build());
    }

    @Override
    public void cancelBooking(BookingDto bookingDto) {
        sessionService.decrementSessionCount(bookingDto.getSession());
        notificationService.unsubscribeFromNotifications(
                Notification.builder()
                        .client(bookingDto.getClient())
                        .session(bookingDto.getSession()).build());
    }
}
