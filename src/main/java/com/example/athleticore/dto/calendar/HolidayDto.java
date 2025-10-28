package com.example.athleticore.dto.calendar;

import lombok.Data;

@Data
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
