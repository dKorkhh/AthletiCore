package com.example.athleticore.service.impl.schedule;

import com.example.athleticore.dto.WeekScheduleDto;
import com.example.athleticore.entity.Schedule;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.repository.ScheduleRepository;
import com.example.athleticore.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock ScheduleRepository scheduleRepository;
    @Mock SessionRepository sessionRepository;

    @InjectMocks ScheduleServiceImpl scheduleService;

    @Test
    void addSessionToSchedule_success() {
        User trainer = new User();
        trainer.setId(5L);

        Session session = new Session();
        session.setTrainer(trainer);

        Schedule sch1 = new Schedule();
        Schedule sch2 = new Schedule();

        when(scheduleRepository.findByTrainerId(5L))
                .thenReturn(List.of(sch1, sch2));

        scheduleService.addSessionToSchedule(session);

        verify(scheduleRepository).saveAll(anyList());
        assertThat(sch1.getSessions()).contains(session);
        assertThat(sch2.getSessions()).contains(session);
    }

    @Test
    void removeSessionFromSchedule_success() {
        Schedule schedule = new Schedule();
        Session session = new Session();
        session.setSchedule(schedule);

        scheduleService.removeSessionFromSchedule(session);

        verify(scheduleRepository).save(schedule);
    }

    @Test
    void removeSessionFromSchedule_nullSchedule() {
        Session session = new Session();

        scheduleService.removeSessionFromSchedule(session);

        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void getWeekSchedule_onlyRepeatedSessions() {
        LocalDate start = LocalDate.of(2025, 1, 6);

        User trainer = new User();
        trainer.setId(1L);

        Session repeated = new Session();
        repeated.setRepeat(true);
        repeated.setDate(LocalDateTime.of(2025,1,1,10,0));
        repeated.setTrainer(trainer);

        when(sessionRepository.findSessionsByTrainerId(1L))
                .thenReturn(List.of(repeated));

        WeekScheduleDto dto = scheduleService.getWeekSchedule(1L, start);

        assertThat(dto.getSessionsByDay().get(DayOfWeek.WEDNESDAY)).hasSize(1);
    }

    @Test
    void getWeekSchedule_onlyOneTimeSessionsInRange() {
        LocalDate start = LocalDate.of(2025,1,6);

        User trainer = new User();
        trainer.setId(1L);

        Session s = new Session();
        s.setRepeat(false);
        s.setDate(LocalDateTime.of(2025,1,8,15,0)); // Wednesday
        s.setTrainer(trainer);

        when(sessionRepository.findSessionsByTrainerId(1L))
                .thenReturn(List.of(s));

        WeekScheduleDto dto = scheduleService.getWeekSchedule(1L, start);

        assertThat(dto.getSessionsByDay().get(DayOfWeek.WEDNESDAY)).containsExactly(s);
    }

    @Test
    void getWeekSchedule_sessionOutsideRange_excluded() {
        LocalDate start = LocalDate.of(2025,1,6);

        Session s = new Session();
        s.setRepeat(false);
        s.setDate(LocalDateTime.of(2025,1,20,10,0)); // after +7 days

        User trainer = new User();
        trainer.setId(1L);
        s.setTrainer(trainer);

        when(sessionRepository.findSessionsByTrainerId(1L))
                .thenReturn(List.of(s));

        WeekScheduleDto dto = scheduleService.getWeekSchedule(1L, start);

        assertThat(dto.getSessionsByDay().values())
                .allSatisfy(list -> assertThat(list).isEmpty());
    }
}
