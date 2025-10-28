package com.example.athleticore.entity;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.BookingStatus;

public class Booking {
    private Long id;
    private User client;
    private Session session;
    private BookingStatus bookingStatus;
}
