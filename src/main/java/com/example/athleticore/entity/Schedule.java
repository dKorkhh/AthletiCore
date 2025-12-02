package com.example.athleticore.entity;

import com.example.athleticore.entity.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User trainer;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Session> sessions = new HashSet<>();

    public void addSession(Session s) {
        sessions.add(s);
        s.setSchedule(this);
    }

    public void removeSession(Session s) {
        sessions.remove(s);
        s.setSchedule(null);
    }
}
