package com.example.athleticore.dto.calendar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HolidayDto {
    private String date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean fixed;
    private boolean global;
    private String[] counties;
    private Integer launchYear;
    private String[] types;
}
