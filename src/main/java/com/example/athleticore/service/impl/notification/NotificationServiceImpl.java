package com.example.athleticore.service.impl.notification;

import com.example.athleticore.dto.NotificationDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

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
}
