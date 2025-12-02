package com.example.athleticore.service;

import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(Long sessionId);
    void cancelBooking(BookingDto bookingDto);
    public List<Booking> getAllBooking();
    List<Booking> findBookingsByCurrentUser();
    List<Booking> findBookingsBySessionId(Long id);
    void deleteBookingBySessionId(Long id);
}
