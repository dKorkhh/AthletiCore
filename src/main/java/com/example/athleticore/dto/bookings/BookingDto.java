package com.example.athleticore.dto.bookings;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long sessionId;
    @DateIsNotHolidayValidator
    private LocalDateTime date;
}
