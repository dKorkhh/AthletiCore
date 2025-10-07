package com.example.athleticore.service.impl.notification;

import com.example.athleticore.dto.NotificationDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void subscribeToNotifications(NotificationDto notification) {

    }

    @Override
    public void unsubscribeFromNotifications(NotificationDto notification) {

    }

    @Override
    public void sendNotification(User client, String message) {

    }
}
