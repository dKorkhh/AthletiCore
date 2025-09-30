package com.example.athleticore.service;

import com.example.athleticore.dto.user.User;
import com.example.athleticore.entity.Notification;

public interface NotificationService {
    void subscribeToNotifications(Notification notification);
    void unsubscribeFromNotifications(Notification notification);
    void sendNotification(User user, String message);
}
