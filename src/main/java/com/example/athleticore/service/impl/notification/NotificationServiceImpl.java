package com.example.athleticore.service.impl.notification;

import com.example.athleticore.dto.NotificationDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.service.NotificationService;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final BookingServiceImpl bookingService;
    private final UserServiceImpl userService;

    @Override
    public void sendNotification(User client, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(client.getEmail());
        message.setSubject("Registration");
        message.setText(messageText);

        mailSender.send(message);
    }

    @Override
    public void sendBookingCanceled(Booking booking) {
        User client = booking.getClient();

        String message = "Ваше бронювання '"
                + booking.getSession().getName()
                + "' на "
                + booking.getSession().getDate()
                + " було автоматично скасовано.";

        sendNotification(client, message);
    }

    @Transactional
    public void sendNotificationBeforeSession(long timeRemain) {
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings = bookingService.getAllBookingWithSession()
                .stream()
                .filter(book -> {
                    long diff = Duration.between(now, book.getSession().getDate()).getSeconds();
                    return diff > 0 && diff <= timeRemain;
                })
                .toList();

        bookings.forEach(book -> {
            User client = book.getClient();
            Session session = book.getSession();

            String msg = """
                Нагадування!
                У вас тренування: %s
                Час: %s
                Залишилось приблизно 1 година.
                """.formatted(
                    session.getName(),
                    session.getDate().format(DateTimeFormatter.ofPattern("dd.MM HH:mm"))
            );

            sendNotification(client, msg);
        });
    }

    public void sendNotificationToAllUser() {
        userService.getAllUser()
                .forEach(user -> sendNotification(user, "News today"));
    }
}
