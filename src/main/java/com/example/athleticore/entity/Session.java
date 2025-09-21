package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.SessionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Session {
    private Long id;
    private String name;
    private String description;
    private SessionType sessionType;
    private LocalDate date;
    private int duration;
    private Trainer trainer;
    private Category category;
    private Difficulty difficulty;
    private int maxParticipants;
}
