package com.example.athleticore.controller.schedule;

import com.example.athleticore.dto.WeekScheduleDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.schedule.ScheduleServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;
    private final UserServiceImpl userService;

    @GetMapping
    public String viewSchedule(Model model) {
        User trainer = userService.getCurrentUser();

        WeekScheduleDto week = scheduleService.getWeekSchedule(trainer.getId());

        model.addAttribute("week", week);

        return "schedule/calendar";
    }
}
