package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.BookingStatus;
import com.example.athleticore.repository.BookingRepository;
import com.example.athleticore.service.BookingService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private NotificationServiceImpl notificationService;

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


}
