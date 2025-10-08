package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Difficulty;
import com.example.athleticore.enums.SessionType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

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

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    private LocalDate date;

    private int duration;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "max_participants")
    private int maxParticipants;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
}

