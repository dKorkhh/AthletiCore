package com.example.athleticore.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.item.ExecutionContext;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookingCleanupJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ExpiredBookingService expiredBookingService;

    @Bean
    public Job bookingCleanupJob() {
        return new JobBuilder("bookingCleanupJob", jobRepository)
                .start(markExpiredBookingsStep())
                .next(notifyUsersStep())
                .build();
    }

    @Bean
    public Step markExpiredBookingsStep() {
        return new StepBuilder("markExpiredBookingsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {

                    List<Long> expiredIds = expiredBookingService.markExpiredBookings();

                    ExecutionContext ctx = chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobExecution()
                            .getExecutionContext();

                    ctx.put("expiredBookingIds", expiredIds);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step notifyUsersStep() {
        return new StepBuilder("notifyUsersStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {

                    ExecutionContext ctx = chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobExecution()
                            .getExecutionContext();

                    @SuppressWarnings("unchecked")
                    List<Long> expiredIds = (List<Long>) ctx.get("expiredBookingIds");

                    expiredBookingService.notifyUsersAboutCancellation(expiredIds);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
