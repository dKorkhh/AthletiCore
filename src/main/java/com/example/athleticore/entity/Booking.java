package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Client;
import com.example.athleticore.enums.BookingStatus;

public class Booking {
    private Long id;
    private Client client;
    private Session session;
    private BookingStatus bookingStatus;
}
