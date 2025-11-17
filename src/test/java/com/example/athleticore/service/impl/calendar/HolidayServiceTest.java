package com.example.athleticore.service.impl.calendar;

import com.example.athleticore.dto.calendar.HolidayDto;
import com.example.athleticore.exception.data.CallApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HolidayService holidayService;

    private static final String HOLIDAY_API_URL = "https://api.example.com/holidays/%s/%s";
    private static final String REGION = "US";
    private static final int CURRENT_YEAR = 2025;

    @BeforeEach
    void setUp() {
        holidayService.setHOLIDAY_API_URL(HOLIDAY_API_URL);
        holidayService.setRegion(REGION);
    }

    @Test
    void getListOfHolidays_Success() {
        HolidayDto[] holidaysArray = {HolidayDto.builder().name("New Year").build()};
        ResponseEntity<HolidayDto[]> response = new ResponseEntity<>(holidaysArray, HttpStatus.OK);

        String expectedUrl = String.format(HOLIDAY_API_URL, CURRENT_YEAR, REGION);
        when(restTemplate.getForEntity(ArgumentMatchers.eq(expectedUrl), ArgumentMatchers.eq(HolidayDto[].class)))
                .thenReturn(response);

        List<HolidayDto> result = holidayService.getListOfHolidays();

        assertEquals(1, result.size());
        assertEquals("New Year", result.get(0).getName());
    }

    @Test
    void getListOfHolidays_Failure_ThrowsException() {
        ResponseEntity<HolidayDto[]> response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        String expectedUrl = String.format(HOLIDAY_API_URL, CURRENT_YEAR, REGION);
        when(restTemplate.getForEntity(ArgumentMatchers.eq(expectedUrl), ArgumentMatchers.eq(HolidayDto[].class)))
                .thenReturn(response);

        CallApiException exception = assertThrows(CallApiException.class, () -> holidayService.getListOfHolidays());
        assertEquals("Failed to fetch holidays", exception.getMessage());
    }
}