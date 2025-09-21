package com.example.athleticore.service.impl.calendar;

import com.example.athleticore.utils.validation.DateIsNotHolidayValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DateIsNotHolidayValidate implements ConstraintValidator<DateIsNotHolidayValidator, LocalDate> {
    private final HolidayService holidayService;

    @Value("${spring.application.holiday-api.date-format}")
    private String dateFormat;

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        boolean isHoliday = holidayService.getListOfHolidays()
                .stream()
                .anyMatch(holiday -> LocalDate.parse(holiday.getDate().trim(), formatter).equals(localDate));

        if (isHoliday) {
            context.buildConstraintViolationWithTemplate("Date " + localDate + " is a holiday.")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}
