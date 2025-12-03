package com.example.athleticore.batch;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BookingCleanupScheduler {

    private final JobLauncher jobLauncher;
    private final Job bookingCleanupJob;
    private static final Logger logger = LogManager.getLogger(BookingCleanupScheduler.class);

    @Scheduled(cron = "0 0 * * * *")
    public void runBookingCleanupJobOnSchedule() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("trigger", "scheduled")
                .toJobParameters();

        JobExecution execution = jobLauncher.run(bookingCleanupJob, params);
        logger.info("Scheduled bookingCleanupJob started: {}", execution.getId());
    }
}
