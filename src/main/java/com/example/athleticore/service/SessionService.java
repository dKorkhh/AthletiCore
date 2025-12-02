package com.example.athleticore.service;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;

import java.util.List;

public interface SessionService {
    void incrementSessionCount(Session sessionId);
    void decrementSessionCount(Session sessionId);
    Session createSession(SessionDto session);
    SessionDto getSessionDtoById(Long id);
    List<Session> getSessions();
    void updateSession(Long id, SessionDto sessionDto);
    Session getSessionById(Long id);
    List<Session> getSessionByTrainer(User trainer);
    void deleteSessionById(Long id);
}
