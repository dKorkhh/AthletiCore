package com.example.athleticore.service.schedular;

import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedularService {
    private final NotificationServiceImpl notificationService;

    @Scheduled(
            cron = "0 0 9 * * *",
            zone = "${spring.scheduler.track-currency.zone:Europe/Kyiv}"
    )
    public void fetchCurrencyRates() {
        notificationService.sendNotificationToAllUser();
    }

    @Scheduled(fixedRate = 3600000)
    public void runHourlyTask() {
        notificationService.sendNotificationBeforeSession(3600);
    }
}
