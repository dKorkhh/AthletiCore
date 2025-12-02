package com.example.athleticore.repository;

import com.example.athleticore.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByTrainerId(Long trainerId);
}
