package com.example.athleticore.controller.notification;

import lombok.RequiredArgsConstructor;
import org.example.springbootstarternotification.NotificationDefaultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationDefaultService notificationService;

    @GetMapping("/")
    public void sendNotification() {
        notificationService.sendNotification("TestUser", "Test notification");
    }
}
