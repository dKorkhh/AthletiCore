package com.example.athleticore.service;

import com.example.athleticore.dto.bookings.BookingDto;

public interface BookingService {
    void createBooking(BookingDto bookingDto);
    void cancelBooking(BookingDto bookingDto);
}
