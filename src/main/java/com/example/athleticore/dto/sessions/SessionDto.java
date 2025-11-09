package com.example.athleticore.dto.sessions;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.SessionType;
import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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
    private LocalDateTime date;
    private boolean isRepeat;
    private int duration;
    @NotNull
    private UserDto trainer;
    private Difficulty difficulty;
    @NotNull
    private Category category;
    private int maxParticipants;
}
