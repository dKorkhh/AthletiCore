package com.example.athleticore.dto.bookings;

import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long sessionId;
    @DateIsNotHolidayValidator
    private LocalDateTime date;
}
