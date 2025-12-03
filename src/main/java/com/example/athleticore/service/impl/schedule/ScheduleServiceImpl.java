package com.example.athleticore.service.impl.schedule;

import com.example.athleticore.dto.WeekScheduleDto;
import com.example.athleticore.entity.Schedule;
import com.example.athleticore.entity.Session;
import com.example.athleticore.repository.ScheduleRepository;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public void addSessionToSchedule(Session session) {

        Long trainerId = session.getTrainer().getId();

        List<Schedule> schedules = scheduleRepository.findByTrainerId(trainerId);

        for (Schedule s : schedules) {
            s.addSession(session);
        }

        scheduleRepository.saveAll(schedules);
    }

    @Transactional
    public void removeSessionFromSchedule(Session session) {
        Schedule schedule = session.getSchedule();
        if (schedule != null) {
            schedule.removeSession(session);
            scheduleRepository.save(schedule);
        }
    }

    public WeekScheduleDto getWeekSchedule(Long trainerId, LocalDate startOfWeek) {

        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = start.plusDays(7);

        List<Session> sessions = sessionRepository.findSessionsByTrainerId(trainerId);

        Map<DayOfWeek, List<Session>> map = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, new ArrayList<>());
        }

        for (Session s : sessions) {
            if (s.isRepeat()) {
                DayOfWeek targetDay = s.getDate().getDayOfWeek();
                LocalDate targetDate = startOfWeek.with(targetDay);

                LocalDateTime repeated = LocalDateTime.of(
                        targetDate,
                        s.getDate().toLocalTime()
                );

                Session copied = copySessionWithDate(s, repeated);
                map.get(targetDay).add(copied);
            }

            else if (!s.isRepeat() &&
                    !s.getDate().isBefore(start) &&
                    !s.getDate().isAfter(end)) {

                map.get(s.getDate().getDayOfWeek()).add(s);
            }
        }

        WeekScheduleDto dto = new WeekScheduleDto();
        dto.setSessionsByDay(map);
        dto.setStartOfWeek(startOfWeek);
        return dto;
    }


    private Session copySessionWithDate(Session original, LocalDateTime newDate) {
        return Session.builder()
                .id(original.getId())
                .name(original.getName())
                .description(original.getDescription())
                .sessionType(original.getSessionType())
                .date(newDate)
                .isRepeat(original.isRepeat())
                .duration(original.getDuration())
                .trainer(original.getTrainer())
                .category(original.getCategory())
                .difficulty(original.getDifficulty())
                .maxParticipants(original.getMaxParticipants())
                .schedule(original.getSchedule())
                .build();
    }
}
