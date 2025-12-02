package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.exception.data.NoDataFoundException;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.SessionMapper;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.SessionService;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import com.example.athleticore.utils.NotifyTrainerOnCreate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final UserServiceImpl userService;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final UserMapper userMapper;

    @Override
    public void incrementSessionCount(Session sessionId) {
        // get from db and increment count
    }

    @Override
    public void decrementSessionCount(Session sessionId) {

    }

    @Override
    @NotifyTrainerOnCreate
    @CacheEvict("sessions")
    public Session createSession(SessionDto session) {
        Session sessionEntity = sessionMapper.toSessionEntity(session);

        User trainer = userService.getUserById(session.getTrainerId());

        sessionEntity.setTrainer(trainer);
        sessionRepository.save(sessionEntity);

        return sessionEntity;
    }

    @Override
    public SessionDto getSessionDtoById(Long id) {
        Session session = sessionRepository.findSessionById(id)
                .orElseThrow(() -> new NoDataFoundException("No such session with id #" + id));

        return sessionMapper.toSessionDto(session);
    }

    @Cacheable("sessions")
    @Override
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public void updateSession(Long id, SessionDto dto) {
        Session s = getSessionById(id);

        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setSessionType(dto.getSessionType());
        s.setDate(dto.getDate());
        s.setRepeat(dto.isRepeat());
        s.setDuration(dto.getDuration());
        s.setTrainer(userService.getUserById(dto.getTrainerId()));
        s.setCategory(dto.getCategory());
        s.setDifficulty(dto.getDifficulty());
        s.setMaxParticipants(dto.getMaxParticipants());

        sessionRepository.save(s);
    }

    @Override
    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("No session with id = ".concat(String.valueOf(id))));
    }

    @Override
    public List<Session> getSessionByTrainer(User trainer) {
        return sessionRepository.findSessionsByTrainer(trainer);
    }

    @Override
    @Transactional
    @CacheEvict(value = "sessions", allEntries = true)
    public void deleteSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (session.getSchedule() != null) {
            session.setSchedule(null);
        }
        sessionRepository.deleteById(id);
    }
}
