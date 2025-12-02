package com.example.athleticore.dto;

import com.example.athleticore.entity.Session;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
public class WeekScheduleDto {
    private Map<DayOfWeek, List<Session>> sessionsByDay;
}
