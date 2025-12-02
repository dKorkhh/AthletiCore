package com.example.athleticore.repository;

import com.example.athleticore.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByClientId(Long clientId);
    List<Booking> findBookingsByClientEmail(String clientEmail);

    Optional<Booking> findBookingsById(Long id);

    List<Booking> findBookingsBySessionId(Long sessionId);

    void deleteBookingsBySessionId(Long sessionId);
}
