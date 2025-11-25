package com.example.athleticore.entity;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.SessionType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    private LocalDateTime date;

    private boolean isRepeat;

    private int duration;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User trainer;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "max_participants")
    private int maxParticipants;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = true)
    private Schedule schedule;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}

