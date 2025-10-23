package com.example.athleticore.config;

import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.example.springbootstarternotification.NotificationDefaultService;
import org.springdoc.core.models.GroupedOpenApi;
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

    /*@Bean
    @ConditionalOnMissingBean(NotificationDefaultService.class)
    public NotificationServiceImpl defaultNotificationService() {
        return new NotificationServiceImpl();
    }*/

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Athleticore API")
                        .version("1.0.0")
                        .description("API documentation for Athleticore Spring Boot application"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .packagesToScan("com.example.athleticore")
                .build();
    }
}
