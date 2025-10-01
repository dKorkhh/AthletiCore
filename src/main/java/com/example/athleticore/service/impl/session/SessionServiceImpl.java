package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.service.SessionService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final NotificationServiceImpl notificationService;

    @Override
    public void incrementSessionCount(Session sessionId) {
        // get from db and increment count
    }

    @Override
    public void decrementSessionCount(Session sessionId) {

    }

    @Override
    public void createSession(SessionDto session) {
        //create and add to db
        notificationService.sendNotification(session.getTrainer(), "New session created");
    }
}
