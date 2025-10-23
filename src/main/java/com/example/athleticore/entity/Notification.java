package com.example.athleticore.entity;

import com.example.athleticore.entity.users.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDate date;

    @ManyToMany(mappedBy = "notifications")
    private Set<Client> clients;

    @OneToOne
    @JoinColumn(name = "session_id")
    private Session session;
}

