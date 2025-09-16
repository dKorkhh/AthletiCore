package com.example.athleticore.Entity;

import com.example.athleticore.Entity.users.Trainer;
import com.example.athleticore.enums.SessionType;

import java.time.LocalDate;

public class Session {
    private Long id;
    private SessionType sessionType;
    private LocalDate date;
    private int duration;
    private Trainer trainer;
    private int maxParticipants;
}
