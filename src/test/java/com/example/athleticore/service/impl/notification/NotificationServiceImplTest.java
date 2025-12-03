package com.example.athleticore.service.impl.notification;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock JavaMailSender mailSender;
    @Mock BookingServiceImpl bookingService;
    @Mock UserServiceImpl userService;

    @InjectMocks NotificationServiceImpl notificationService;

    @Captor ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    void sendNotification_success() {
        User user = new User();
        user.setEmail("client@mail.com");

        notificationService.sendNotification(user, "Hello");

        verify(mailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getTo()).contains("client@mail.com");
        assertThat(messageCaptor.getValue().getText()).isEqualTo("Hello");
    }

    @Test
    void sendBookingCanceled_success() {
        User user = new User();
        user.setEmail("u@mail.com");

        Session session = new Session();
        session.setName("Yoga");
        session.setDate(LocalDateTime.of(2025,1,1,10,0));

        Booking booking = new Booking();
        booking.setClient(user);
        booking.setSession(session);

        notificationService.sendBookingCanceled(booking);

        verify(mailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getText())
                .contains("Yoga");
    }

    @Test
    void sendNotificationBeforeSession_success() {
        User user = new User();
        user.setEmail("client@mail");

        Session session = new Session();
        session.setName("Boxing");
        session.setDate(LocalDateTime.now().plusMinutes(30));

        Booking b = new Booking();
        b.setClient(user);
        b.setSession(session);

        when(bookingService.getAllBookingWithSession()).thenReturn(List.of(b));

        notificationService.sendNotificationBeforeSession(3600);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendNotificationToAllUser_success() {
        User u1 = new User();
        u1.setEmail("a@mail.com");

        User u2 = new User();
        u2.setEmail("b@mail.com");

        when(userService.getAllUser()).thenReturn(List.of(u1, u2));

        notificationService.sendNotificationToAllUser();

        verify(mailSender, times(2)).send(any(SimpleMailMessage.class));
    }
}
