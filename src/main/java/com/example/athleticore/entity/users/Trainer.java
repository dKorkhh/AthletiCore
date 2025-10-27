package com.example.athleticore.entity.users;

import com.example.athleticore.entity.Notification;
import com.example.athleticore.entity.Schedule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "trainers")
public class Trainer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Schedule schedule;

    @ManyToMany
    @JoinTable(
            name = "trainer_notifications",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private Set<Notification> notifications = new HashSet<>();
}
