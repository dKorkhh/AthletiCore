package com.example.athleticore.aspect;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.UserRepository;
import com.example.athleticore.service.NotificationService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TrainerNotificationAspect {

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final UserMapper userMapper;
    private static final Logger logger = LogManager.getLogger(TrainerNotificationAspect.class);

    @AfterReturning(pointcut = "@annotation(com.example.athleticore.utils.NotifyTrainerOnCreate)", returning = "createdSession")
    public void notifyTrainer(JoinPoint joinPoint, Object createdSession) {
        String trainerEmail = extractTrainerEmail(joinPoint.getArgs());

        User trainer = userRepository.getUserByEmail(trainerEmail)
                .orElse(null);

        if (trainer == null) return;

        String message = "Вас призначено тренером нової сесії: " +
                createdSession.toString();
        logger.log(Level.INFO, message);
        //notificationService.sendEmailNotification(userMapper.toDto(trainer));
    }

    private String extractTrainerEmail(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof SessionDto dto) {
                return dto.getTrainer().getEmail();
            }
        }
        return null;
    }
}
