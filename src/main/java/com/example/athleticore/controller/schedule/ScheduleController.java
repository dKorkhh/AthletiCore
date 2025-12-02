package com.example.athleticore.controller.schedule;

import com.example.athleticore.dto.WeekScheduleDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.schedule.ScheduleServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Controller
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;
    private final UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public String viewSchedule(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               LocalDate start,
                               Model model) {

        User trainer = userService.getCurrentUser();

        if (start == null) {
            start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }

        WeekScheduleDto week = scheduleService.getWeekSchedule(trainer.getId(), start);

        model.addAttribute("week", week);
        model.addAttribute("startDate", start);
        return "schedule/calendar";
    }
}
