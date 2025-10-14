package com.example.athleticore.config;

import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import org.example.springbootstarternotification.NotificationDefaultService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(NotificationDefaultService.class)
    public NotificationServiceImpl defaultNotificationService() {
        return new NotificationServiceImpl();
    }
}
