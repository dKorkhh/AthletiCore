package com.example.athleticore.dto.bookings;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    private Session session;
    private UserDto client;
    @DateIsNotHolidayValidator
    private LocalDate date;
}
