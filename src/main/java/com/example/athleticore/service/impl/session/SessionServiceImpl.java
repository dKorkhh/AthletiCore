package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.exception.data.NoDataFoundException;
import com.example.athleticore.exception.limit.SessionTimeOutOfRangeException;
import com.example.athleticore.mapper.SessionMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.SessionService;
import com.example.athleticore.service.impl.schedule.ScheduleServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import com.example.athleticore.utils.NotifyTrainerOnCreate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final UserServiceImpl userService;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ScheduleServiceImpl scheduleService;

    @Override
    @NotifyTrainerOnCreate
    @CacheEvict(value = "sessions", allEntries = true)
    public Session createSession(SessionDto session) {
        validateSessionTime(session.getDate());
        Session sessionEntity = sessionMapper.toSessionEntity(session);

        User trainer = userService.getUserById(session.getTrainerId());

        sessionEntity.setTrainer(trainer);
        sessionRepository.save(sessionEntity);
        scheduleService.addSessionToSchedule(sessionEntity);

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
    @CacheEvict(value = "sessions", allEntries = true)
    public void updateSession(Long id, SessionDto dto) {
        Session s = getSessionById(id);

        validateSessionTime(s.getDate());

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
    public void saveAll(List<Session> sessions) {
        sessionRepository.saveAll(sessions);
    }

    @Override
    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("No session with id = ".concat(String.valueOf(id))));
    }

    @Override
    public List<Session> getSessionByTrainerId(Long id) {
        return sessionRepository.findSessionsByTrainerId(id);
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

        scheduleService.removeSessionFromSchedule(session);
        sessionRepository.deleteById(id);
    }

    @Override
    public PageResponse<Session> getSessionsPage(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<Session> result = sessionRepository.findAll(pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    @Override
    public PageResponse<Session> getSessionsByTrainerPage(User trainer, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<Session> result = sessionRepository.findAllByTrainer(trainer, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    private Sort parseSort(String sort) {
        String[] arr = sort.split(",");
        String field = arr[0];
        String direction = arr.length > 1 ? arr[1] : "asc";

        return Sort.by(Sort.Direction.fromString(direction), field);
    }

    private void validateSessionTime(LocalDateTime dateTime) {

        LocalTime time = dateTime.toLocalTime();

        LocalTime min = LocalTime.of(8, 0);
        LocalTime max = LocalTime.of(19, 0);

        if (time.isBefore(min) || time.isAfter(max)) {
            throw new SessionTimeOutOfRangeException(
                    "Час тренування повинен бути між 08:00 та 19:00."
            );
        }
    }

}
