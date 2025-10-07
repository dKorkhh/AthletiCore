package com.example.athleticore.dto;

import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NotificationDto {
    private Client client;
    private Session session;
    private LocalDate date;
}
