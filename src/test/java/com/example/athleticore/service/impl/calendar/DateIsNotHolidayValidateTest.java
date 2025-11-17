package com.example.athleticore.service.impl.calendar;

import com.example.athleticore.dto.calendar.HolidayDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DateIsNotHolidayValidateTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private DateIsNotHolidayValidate validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2025, 11, 9, 12, 0); // November 09, 2025

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(validator, "dateFormat", DATE_FORMAT);
    }

    @Test
    void isValid_NotHoliday_Success_ReturnsTrue() {
        List<HolidayDto> holidays = Collections.emptyList();
        when(holidayService.getListOfHolidays()).thenReturn(holidays);

        boolean result = validator.isValid(TEST_DATE, context);

        assertTrue(result);
        verify(context, never()).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    void isValid_IsHoliday_Failure_ReturnsFalseAndAddsViolation() {
        when(holidayService.getListOfHolidays()).thenReturn(List.of(
                HolidayDto.builder().date("2025-11-09").build())
        );

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);

        boolean result = validator.isValid(TEST_DATE, context);

        assertFalse(result);
        verify(context).buildConstraintViolationWithTemplate("Date " + TEST_DATE + " is a holiday.");
        verify(context).disableDefaultConstraintViolation();
        verify(builder).addConstraintViolation();
    }
}