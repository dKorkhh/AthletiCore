package com.example.athleticore.service;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.users.User;

public interface NotificationService {
    void sendNotification(User user, String message);
    void sendBookingCanceled(Booking booking);
}
