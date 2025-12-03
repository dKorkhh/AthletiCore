package com.example.athleticore.service;

import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(Long sessionId);
    List<Booking> findBookingsByCurrentUser();
    List<Booking> findBookingsBySessionId(Long id);
    List<Booking> getAllBookingWithSession();

    List<Booking> findExpiredBookings(List<BookingStatus> statuses, LocalDateTime threshold);

    List<Booking> findAllById(List<Long> bookingIds);

    void saveAll(List<Booking> expired);
     void cancelBooking(Long bookingId);
}
