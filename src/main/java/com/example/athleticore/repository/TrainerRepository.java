package com.example.athleticore.repository;

import com.example.athleticore.entity.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
