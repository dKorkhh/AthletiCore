package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.dto.TimeSlot;

import java.util.List;

public class Schedule {
    private Long id;
    private Trainer trainer;
    private List<TimeSlot> timeSlots;
}
