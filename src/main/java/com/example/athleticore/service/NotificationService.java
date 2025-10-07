package com.example.athleticore.service;

import com.example.athleticore.dto.NotificationDto;
import com.example.athleticore.entity.users.User;

public interface NotificationService {
    void subscribeToNotifications(NotificationDto notification);
    void unsubscribeFromNotifications(NotificationDto notification);
    void sendNotification(User user, String message);
}
