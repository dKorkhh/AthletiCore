package com.example.athleticore.service;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;

public interface SessionService {
    void incrementSessionCount(Session sessionId);
    void decrementSessionCount(Session sessionId);
    Session createSession(SessionDto session);
}
