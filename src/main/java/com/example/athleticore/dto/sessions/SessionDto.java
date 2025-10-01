package com.example.athleticore.dto.sessions;

import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.SessionType;
import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SessionDto {
    @NotNull
    @Size(max = 12)
    private String name;
    @Size(max = 60)
    private String description;
    @NotNull
    private SessionType sessionType;
    @NotNull
    @DateIsNotHolidayValidator
    private LocalDate date;
    private int duration;
    @NotNull
    private Trainer trainer;
    private Difficulty difficulty;
    @NotNull
    private Category category;
    private int maxParticipants;
}
