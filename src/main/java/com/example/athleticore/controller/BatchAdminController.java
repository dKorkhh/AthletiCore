package com.example.athleticore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/batch")
@RequiredArgsConstructor
public class BatchAdminController {

    private final JobLauncher jobLauncher;
    private final Job bookingCleanupJob;

    @PostMapping("/booking-cleanup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> runBookingCleanupManually() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("trigger", "manual")
                .toJobParameters();

        JobExecution execution = jobLauncher.run(bookingCleanupJob, params);

        return ResponseEntity.ok(
                "bookingCleanupJob started, executionId = " + execution.getId()
        );
    }
}
