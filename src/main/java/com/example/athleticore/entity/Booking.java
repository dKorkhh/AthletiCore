package com.example.athleticore.entity;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Entity
@Table(name = "bookings")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    private Session session;

    private BookingStatus bookingStatus;
}
