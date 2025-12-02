package com.example.athleticore.repository;

import com.example.athleticore.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTrainerId(Long trainerId);
}
