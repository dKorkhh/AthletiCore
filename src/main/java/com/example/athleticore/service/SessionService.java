package com.example.athleticore.service;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;

import java.util.List;

public interface SessionService {
    Session createSession(SessionDto session);
    SessionDto getSessionDtoById(Long id);
    List<Session> getSessions();
    void updateSession(Long id, SessionDto sessionDto);
    void saveAll(List<Session> sessions);
    Session getSessionById(Long id);
    List<Session> getSessionByTrainerId(Long id);
    void deleteSessionById(Long id);
    PageResponse<Session> getSessionsPage(int page, int size, String sort);
    PageResponse<Session> getSessionsByTrainerPage(User trainer, int page, int size, String sort);
}
