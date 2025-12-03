package com.example.athleticore.service.impl.session;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.exception.data.NoDataFoundException;
import com.example.athleticore.exception.limit.SessionTimeOutOfRangeException;
import com.example.athleticore.mapper.SessionMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.impl.schedule.ScheduleServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Mock
    UserServiceImpl userService;
    @Mock
    SessionRepository sessionRepository;
    @Mock
    SessionMapper sessionMapper;
    @Mock
    ScheduleServiceImpl scheduleService;

    @InjectMocks
    SessionServiceImpl sessionService;

    @Test
    void createSession_success() {
        SessionDto dto = SessionDto.builder().build();
        dto.setDate(LocalDateTime.of(2025,1,1,10,0));
        dto.setTrainerId(5L);

        Session sessionEntity = new Session();
        User trainer = new User();

        when(sessionMapper.toSessionEntity(dto)).thenReturn(sessionEntity);
        when(userService.getUserById(5L)).thenReturn(trainer);
        when(sessionRepository.save(sessionEntity)).thenReturn(sessionEntity);

        Session result = sessionService.createSession(dto);

        assertThat(result.getTrainer()).isEqualTo(trainer);
        verify(scheduleService).addSessionToSchedule(sessionEntity);
    }

    @Test
    void createSession_timeOutOfRange() {
        SessionDto dto = SessionDto.builder().build();
        dto.setDate(LocalDateTime.of(2025,1,1,22,0));

        assertThatThrownBy(() -> sessionService.createSession(dto))
                .isInstanceOf(SessionTimeOutOfRangeException.class);
    }

    @Test
    void getSessionDtoById_success() {
        Session session = new Session();
        SessionDto dto = SessionDto.builder().build();

        when(sessionRepository.findSessionById(1L)).thenReturn(Optional.of(session));
        when(sessionMapper.toSessionDto(session)).thenReturn(dto);

        SessionDto result = sessionService.getSessionDtoById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getSessionDtoById_notFound() {
        when(sessionRepository.findSessionById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.getSessionDtoById(1L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void getSessions_success() {
        Session s = new Session();
        when(sessionRepository.findAll()).thenReturn(List.of(s));

        List<Session> result = sessionService.getSessions();

        assertThat(result).containsExactly(s);
    }

    @Test
    void updateSession_success() {
        LocalDateTime validDate = LocalDateTime.of(2025,1,1,10,0);

        Session saved = new Session();
        saved.setId(1L);
        saved.setDate(validDate);

        SessionDto dto = SessionDto.builder().build();
        dto.setName("New");
        dto.setDescription("D");
        dto.setTrainerId(2L);
        dto.setDate(validDate);

        User trainer = new User();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(saved));
        when(userService.getUserById(2L)).thenReturn(trainer);

        sessionService.updateSession(1L, dto);

        assertThat(saved.getName()).isEqualTo("New");
        assertThat(saved.getTrainer()).isEqualTo(trainer);
        verify(sessionRepository).save(saved);
    }

    @Test
    void getSessionById_success() {
        Session s = new Session();
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(s));

        Session result = sessionService.getSessionById(1L);

        assertThat(result).isEqualTo(s);
    }

    @Test
    void getSessionById_notFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.getSessionById(1L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void getSessionByTrainerId_success() {
        User trainer = new User();
        trainer.setId(5L);

        Session s1 = new Session();
        Session s2 = new Session();

        when(sessionRepository.findSessionsByTrainerId(5L))
                .thenReturn(List.of(s1, s2));

        List<Session> result = sessionService.getSessionByTrainerId(5L);

        assertThat(result).containsExactly(s1, s2);
    }

    @Test
    void deleteSessionById_success() {
        Session s = new Session();
        s.setId(1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(s));

        sessionService.deleteSessionById(1L);

        verify(scheduleService).removeSessionFromSchedule(s);
        verify(sessionRepository).deleteById(1L);
    }

    @Test
    void getSessionsPage_success() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        Session s = new Session();
        Page<Session> page = new PageImpl<>(List.of(s), pageable, 1);

        when(sessionRepository.findAll(any(Pageable.class))).thenReturn(page);

        PageResponse<Session> response = sessionService.getSessionsPage(0, 5, "id,asc");

        assertThat(response.getContent()).containsExactly(s);
        assertThat(response.getPage()).isEqualTo(0);
        assertThat(response.getTotalPages()).isEqualTo(1);
    }

    @Test
    void getSessionsByTrainerPage_success() {
        User trainer = new User();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        Session s = new Session();
        Page<Session> page = new PageImpl<>(List.of(s), pageable, 1);

        when(sessionRepository.findAllByTrainer(eq(trainer), any(Pageable.class)))
                .thenReturn(page);

        PageResponse<Session> response =
                sessionService.getSessionsByTrainerPage(trainer, 0, 5, "id,asc");

        assertThat(response.getContent()).containsExactly(s);
    }

    @Test
    void parseSort_valid() {
        Sort sort = invokeParseSort("date,desc");

        assertThat(sort.getOrderFor("date").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void parseSort_defaultAsc() {
        Sort sort = invokeParseSort("name");

        assertThat(sort.getOrderFor("name").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    private Sort invokeParseSort(String sort) {
        return org.springframework.test.util.ReflectionTestUtils
                .invokeMethod(sessionService, "parseSort", sort);
    }
}
