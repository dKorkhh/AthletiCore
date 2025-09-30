package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private Long id;
    private Client client;
    private Session session;
}
