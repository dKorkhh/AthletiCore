package com.example.athleticore.service;

import com.example.athleticore.dto.bookings.BookingDto;
import com.example.athleticore.dto.bookings.BookingResponseDTO;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(Long sessionId);
    void cancelBooking(BookingDto bookingDto);
    public List<Booking> getAllBooking();
    List<Booking> findBookingsByCurrentUser();
    List<Booking> findBookingsBySessionId(Long id);
    void deleteBookingBySessionId(Long id);

    List<Booking> getAllBookingWithSession();

    List<Booking> findExpiredBookings(List<BookingStatus> statuses, LocalDateTime threshold);

    List<Booking> findAllById(List<Long> bookingIds);

    void saveAll(List<Booking> expired);
    public void cancelBooking(Long bookingId);
}
