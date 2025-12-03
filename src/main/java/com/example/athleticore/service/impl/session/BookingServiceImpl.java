package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.BookingStatus;
import com.example.athleticore.exception.data.NoDataFoundException;
import com.example.athleticore.exception.limit.AlreadyBookedException;
import com.example.athleticore.exception.limit.MaxParticipantsReachedException;
import com.example.athleticore.repository.BookingRepository;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.BookingService;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserServiceImpl userService;
    private final SessionRepository sessionRepository;

    @Override
    public BookingResponseDTO createBooking(Long sessionId) {
        User client = userService.getCurrentUser();

        boolean exists = bookingRepository.existsByClientIdAndSessionId(client.getId(), sessionId);

        if (exists) {
            throw new AlreadyBookedException("Ви вже записані на це тренування.");
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NoDataFoundException("Session not found"));

        int booked = session.getBookings().size();
        int max = session.getMaxParticipants();

        if (booked >= max) {
            throw new MaxParticipantsReachedException(
                    "На жаль, місця на це тренування вже закінчилися."
            );
        }

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.CONFIRMED)
                .client(client)
                .session(session)
                .build();

        bookingRepository.save(booking);

        return BookingResponseDTO.builder()
                .id(booking.getId())
                .sessionId(session.getId())
                .sessionName(session.getName())
                .sessionDate(session.getDate())
                .clientId(client.getId())
                .clientName(client.getFullName().getFirstName())
                .status(BookingStatus.CONFIRMED)
                .build();
    }

    @Override
    public List<Booking> findBookingsByCurrentUser() {
        String email = userService.getCurrentUser().getEmail();

        if (Strings.isEmpty(email))
            throw new NoDataFoundException("User doesn't have corresponding email");

        return bookingRepository.findBookingsByClientEmail(email);
    }

    @Override
    public List<Booking> findBookingsBySessionId(Long id) {
        return bookingRepository.findBookingsBySessionId(id);
    }

    @Override
    public List<Booking> getAllBookingWithSession() {
        return bookingRepository.findAllWithSession();
    }

    @Override
    public List<Booking> findExpiredBookings(List<BookingStatus> statuses, LocalDateTime threshold) {
        return bookingRepository.findExpiredBookings(statuses, threshold);
    }

    @Override
    public List<Booking> findAllById(List<Long> bookingIds) {
        return bookingRepository.findAllById(bookingIds);
    }

    @Override
    public void saveAll(List<Booking> expired) {
        bookingRepository.saveAll(expired);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронювання не знайдено"));

        User current = userService.getCurrentUser();
        if (!booking.getClient().getId().equals(current.getId())) {
            throw new NoDataFoundException("Ви не можете скасувати чуже бронювання.");
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            return;
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }

}
