package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.SessionMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.SessionService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final NotificationServiceImpl notificationService;
    private final UserServiceImpl userService;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Override
    public void incrementSessionCount(Session sessionId) {
        // get from db and increment count
    }

    @Override
    public void decrementSessionCount(Session sessionId) {

    }

    @Override
    public Session createSession(SessionDto session) {
        Session sessionEntity = sessionMapper.toSessionEntity(session);

        User trainer = userService.getUserByEmail(session.getTrainer().getEmail())
                .orElseThrow(() -> new NoSuchUserException("No user"));

        sessionEntity.setTrainer(trainer);
        sessionRepository.save(sessionEntity);

        return sessionEntity;
    }
}
