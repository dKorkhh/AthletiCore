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
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock BookingRepository bookingRepository;
    @Mock UserServiceImpl userService;
    @Mock SessionRepository sessionRepository;

    @InjectMocks BookingServiceImpl bookingService;

    @Test
    void createBooking_success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("client@mail.com");

        Session session = new Session();
        session.setId(10L);
        session.setName("Yoga");
        session.setDate(LocalDateTime.now());
        session.setMaxParticipants(5);
        session.setBookings(new ArrayList<>());

        Booking saved = Booking.builder()
                .id(100L)
                .client(user)
                .session(session)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        when(userService.getCurrentUser()).thenReturn(user);
        when(bookingRepository.existsByClientIdAndSessionId(1L, 10L)).thenReturn(false);
        when(sessionRepository.findById(10L)).thenReturn(Optional.of(session));
        when(bookingRepository.save(any())).thenReturn(saved);

        BookingResponseDTO dto = bookingService.createBooking(10L);

        assertThat(dto.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
        assertThat(dto.getSessionId()).isEqualTo(10L);
    }

    @Test
    void createBooking_alreadyBooked() {
        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(bookingRepository.existsByClientIdAndSessionId(1L, 10L)).thenReturn(true);

        assertThatThrownBy(() -> bookingService.createBooking(10L))
                .isInstanceOf(AlreadyBookedException.class);
    }

    @Test
    void createBooking_maxReached() {
        User user = new User();
        user.setId(1L);

        Session session = new Session();
        session.setId(10L);
        session.setMaxParticipants(1);
        session.setBookings(List.of(new Booking()));

        when(userService.getCurrentUser()).thenReturn(user);
        when(bookingRepository.existsByClientIdAndSessionId(1L, 10L)).thenReturn(false);
        when(sessionRepository.findById(10L)).thenReturn(Optional.of(session));

        assertThatThrownBy(() -> bookingService.createBooking(10L))
                .isInstanceOf(MaxParticipantsReachedException.class);
    }

    @Test
    void createBooking_sessionNotFound() {
        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(bookingRepository.existsByClientIdAndSessionId(1L, 10L)).thenReturn(false);
        when(sessionRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.createBooking(10L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void findBookingsByCurrentUser_success() {
        User user = new User();
        user.setEmail("client@mail.com");

        Booking b1 = new Booking();
        Booking b2 = new Booking();

        when(userService.getCurrentUser()).thenReturn(user);
        when(bookingRepository.findBookingsByClientEmail("client@mail.com"))
                .thenReturn(List.of(b1, b2));

        List<Booking> result = bookingService.findBookingsByCurrentUser();

        assertThat(result).containsExactly(b1, b2);
    }

    @Test
    void findBookingsByCurrentUser_noEmail() {
        User user = new User();
        user.setEmail("");

        when(userService.getCurrentUser()).thenReturn(user);

        assertThatThrownBy(() -> bookingService.findBookingsByCurrentUser())
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void cancelBooking_success() {
        User current = new User();
        current.setId(1L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setClient(current);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(userService.getCurrentUser()).thenReturn(current);

        bookingService.cancelBooking(10L);

        assertThat(booking.getBookingStatus()).isEqualTo(BookingStatus.CANCELLED);
        verify(bookingRepository).save(booking);
    }

    @Test
    void cancelBooking_otherUsersBooking() {
        User current = new User();
        current.setId(1L);

        User another = new User();
        another.setId(2L);

        Booking booking = new Booking();
        booking.setClient(another);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(userService.getCurrentUser()).thenReturn(current);

        assertThatThrownBy(() -> bookingService.cancelBooking(10L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void cancelBooking_alreadyCancelled() {
        User current = new User();
        current.setId(1L);

        Booking booking = new Booking();
        booking.setClient(current);
        booking.setBookingStatus(BookingStatus.CANCELLED);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(userService.getCurrentUser()).thenReturn(current);

        bookingService.cancelBooking(10L);

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void findBookingsBySessionId_success() {
        Booking b = new Booking();
        when(bookingRepository.findBookingsBySessionId(5L)).thenReturn(List.of(b));

        List<Booking> result = bookingService.findBookingsBySessionId(5L);

        assertThat(result).containsExactly(b);
    }

    @Test
    void getAllBookingWithSession_success() {
        Booking b = new Booking();
        when(bookingRepository.findAllWithSession()).thenReturn(List.of(b));

        List<Booking> result = bookingService.getAllBookingWithSession();

        assertThat(result).containsExactly(b);
    }

    @Test
    void findExpiredBookings_success() {
        Booking b = new Booking();
        when(bookingRepository.findExpiredBookings(anyList(), any())).thenReturn(List.of(b));

        List<Booking> result = bookingService.findExpiredBookings(
                List.of(BookingStatus.CONFIRMED),
                LocalDateTime.now()
        );

        assertThat(result).containsExactly(b);
    }

    @Test
    void findAllById_success() {
        Booking b = new Booking();
        when(bookingRepository.findAllById(List.of(1L,2L))).thenReturn(List.of(b));

        List<Booking> result = bookingService.findAllById(List.of(1L,2L));

        assertThat(result).containsExactly(b);
    }

    @Test
    void saveAll_success() {
        List<Booking> list = List.of(new Booking());

        bookingService.saveAll(list);

        verify(bookingRepository).saveAll(list);
    }
}
