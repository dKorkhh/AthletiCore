package com.example.athleticore.service.impl.notification;

import com.example.athleticore.dto.NotificationDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.service.NotificationService;
import com.example.athleticore.service.impl.session.BookingServiceImpl;
import com.example.athleticore.service.impl.session.SessionServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final SessionServiceImpl sessionService;
    private final BookingServiceImpl bookingService;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Override
    public void subscribeToNotifications(NotificationDto notification) {

    }

    @Override
    public void unsubscribeFromNotifications(NotificationDto notification) {

    }

    @Override
    public void sendNotification(User client, String message) {
        System.out.println(message);
    }

    @Override
    public void sendEmailNotification(UserDto userDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDto.getEmail());
        message.setSubject("Registration");
        message.setText(
                "Name: " + userDto.getFullName() + "\n" +
                        "Email: " + userDto.getEmail() + "\n" +
                        "Pass: " + userDto.getPassword()
        );

        mailSender.send(message);
    }

    public void sendNotificationBeforeSession(long timeRemain) {
        List<UserDto> userDtos = bookingService.getAllBooking().stream()
                .filter(book -> timeRemain > Duration
                        .between(book.getSession().getDate(), LocalDateTime.now())
                        .getSeconds())
                .map(Booking::getClient)
                .map(userMapper::toDto)
                .toList();

        userDtos.forEach(this::sendEmailNotification);
    }

    public void sendNotificationToAllUser() {
        userService.getAllUser()
                .forEach(user -> sendNotification(user, "News today"));
    }
}
