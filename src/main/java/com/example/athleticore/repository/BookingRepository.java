package com.example.athleticore.repository;

import com.example.athleticore.entity.Booking;
import com.example.athleticore.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByClientId(Long clientId);
    List<Booking> findBookingsByClientEmail(String clientEmail);

    Optional<Booking> findBookingsById(Long id);

    List<Booking> findBookingsBySessionId(Long sessionId);

    @Query("""
           select b
           from Booking b
           where b.bookingStatus in :statuses
             and b.session.date < :threshold
           """)
    List<Booking> findExpiredBookings(List<BookingStatus> statuses,
                                      LocalDateTime threshold);

    @Query("SELECT b FROM Booking b JOIN FETCH b.session JOIN FETCH b.client")
    List<Booking> findAllWithSession();

    boolean existsByClientIdAndSessionId(Long clientId, Long sessionId);
}
