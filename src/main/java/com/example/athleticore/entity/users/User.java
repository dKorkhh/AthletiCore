package com.example.athleticore.entity.users;

import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.entity.Notification;
import com.example.athleticore.entity.Schedule;
import com.example.athleticore.enums.Category;
import com.example.athleticore.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FullName fullName;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Schedule schedule;

    @ManyToMany
    @JoinTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private Set<Notification> notifications = new HashSet<>();
}

