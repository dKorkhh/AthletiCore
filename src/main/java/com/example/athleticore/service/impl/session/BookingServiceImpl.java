package com.example.athleticore.service.impl.session;

import com.example.athleticore.aspect.ExceptionHandlingAspect;
import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.BookingStatus;
import com.example.athleticore.exception.data.NoDataFoundException;
import com.example.athleticore.repository.BookingRepository;
import com.example.athleticore.service.BookingService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private static final Logger logger = LogManager.getLogger(ExceptionHandlingAspect.class);

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingResponseDTO createBooking(Long sessionId) {
        User client = userService.getCurrentUser();
        Session session = sessionService.getSessionById(sessionId);

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
    public void cancelBooking(BookingDto bookingDto) {
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findBookingsByCurrentUser() {
        String currentUserEmail = userService.getCurrentUser().getEmail();

        if (Strings.isEmpty(currentUserEmail)) {
            logger.warn("User doesn't have corresponding email");
            throw new NoDataFoundException("User doesn't have corresponding email");
        }

        return bookingRepository.findBookingsByClientEmail(currentUserEmail);
    }

    @Override
    public List<Booking> findBookingsBySessionId(Long id) {
        return bookingRepository.findBookingsBySessionId(id);
    }

    @Override
    public void deleteBookingBySessionId(Long id) {
        bookingRepository.deleteBookingsBySessionId(id);
    }

    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findBookingsByClientId(userId);
    }

    public Booking findBookingById(Long bookingId) {
        return bookingRepository.findBookingsById(bookingId)
                .orElseThrow(() -> new NoDataFoundException("No such booking with id #" + bookingId));
    }
}
