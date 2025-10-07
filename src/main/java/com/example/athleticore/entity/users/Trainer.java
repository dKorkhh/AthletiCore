package com.example.athleticore.entity.users;

import com.example.athleticore.entity.Schedule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
