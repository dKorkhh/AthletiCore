package com.example.athleticore.Entity;

import com.example.athleticore.Entity.users.Trainer;
import com.example.athleticore.dto.TimeSlot;

import java.util.List;

public class Schedule {
    private Long id;
    private Trainer trainer;
    private List<TimeSlot> timeSlots;
}
