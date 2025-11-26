package com.example.athleticore.controller.notification;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.springbootstarternotification.NotificationDefaultService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationDefaultService notificationService;
    private final NotificationServiceImpl notificationServiceS;

    @PostMapping("/")
    public void sendNotification(@RequestBody UserDto userDto) {
        notificationServiceS.sendEmailNotification(userDto);
    }
}
