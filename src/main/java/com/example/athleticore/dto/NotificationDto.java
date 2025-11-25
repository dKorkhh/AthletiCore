package com.example.athleticore.dto;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Session;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NotificationDto {
    private UserDto client;
    private Session session;
    private LocalDate date;
}
