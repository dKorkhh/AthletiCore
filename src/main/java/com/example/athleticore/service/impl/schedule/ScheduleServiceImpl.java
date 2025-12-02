package com.example.athleticore.service.impl.schedule;

import com.example.athleticore.dto.WeekScheduleDto;
import com.example.athleticore.entity.Schedule;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.repository.ScheduleRepository;
import com.example.athleticore.service.ScheduleService;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public void addSessionToSchedule(Session session) {

        Long trainerId = session.getTrainer().getId();

        Schedule schedule = scheduleRepository.findByTrainerId(trainerId)
                .orElseGet(() -> createEmptySchedule(trainerId));

        schedule.addSession(session);

        scheduleRepository.save(schedule);
    }

    private Schedule createEmptySchedule(Long trainerId) {
        Schedule schedule = new Schedule();
        User trainer = userService.getUserById(trainerId);

        schedule.setTrainer(trainer);

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void removeSessionFromSchedule(Session session) {
        Schedule schedule = session.getSchedule();
        if (schedule != null) {
            schedule.removeSession(session);
            scheduleRepository.save(schedule);
        }
    }

    public WeekScheduleDto getWeekSchedule(Long trainerId) {

        Schedule schedule = scheduleRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Map<DayOfWeek, List<Session>> weeklyMap = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            weeklyMap.put(day, new ArrayList<>());
        }

        schedule.getSessions().forEach(session -> {
            DayOfWeek day = session.getDate().getDayOfWeek();
            weeklyMap.get(day).add(session);
        });

        WeekScheduleDto dto = new WeekScheduleDto();
        dto.setSessionsByDay(weeklyMap);

        return dto;
    }

}
