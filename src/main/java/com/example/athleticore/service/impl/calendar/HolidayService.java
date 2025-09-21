package com.example.athleticore.service.impl.calendar;

import com.example.athleticore.dto.calendar.HolidayDto;
import com.example.athleticore.exception.data.CallApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class HolidayService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.application.holiday-api.url}")
    private String HOLIDAY_API_URL;

    @Value("${spring.application.holiday-api.region}")
    private String region;

    public List<HolidayDto> getListOfHolidays() {
        int currentYear = java.time.Year.now().getValue();
        ResponseEntity<HolidayDto[]> response = restTemplate.getForEntity(String.format(HOLIDAY_API_URL, currentYear, region), HolidayDto[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            return Arrays.asList(response.getBody());
        }
        throw new CallApiException("Failed to fetch holidays");
    }
}
