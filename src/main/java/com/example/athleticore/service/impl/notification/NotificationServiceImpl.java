package com.example.athleticore.service.impl.notification;

import com.example.athleticore.dto.user.User;
import com.example.athleticore.entity.Notification;
import com.example.athleticore.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void subscribeToNotifications(Notification notification) {

    }

    @Override
    public void unsubscribeFromNotifications(Notification notification) {

    }

    @Override
    public void sendNotification(User client, String message) {

    }
}
