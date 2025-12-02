package com.example.athleticore.aspect;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Notification;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.UserRepository;
import com.example.athleticore.service.NotificationService;
import com.example.athleticore.service.impl.notification.NotificationServiceImpl;
import com.example.athleticore.service.impl.user.UserServiceImpl;
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
    private final NotificationServiceImpl notificationService;
    private static final Logger logger = LogManager.getLogger(TrainerNotificationAspect.class);

    @AfterReturning(
            pointcut = "@annotation(com.example.athleticore.utils.NotifyTrainerOnCreate)",
            returning = "createdSession"
    )
    public void notifyTrainer(JoinPoint joinPoint, Session createdSession) {

        try {
            if (createdSession == null) {
                logger.warn("NotifyTrainer: createdSession is null");
                return;
            }

            String trainerEmail = extractTrainerEmail(joinPoint.getArgs());

            User trainer = userRepository.getUserByEmail(trainerEmail)
                    .orElse(null);

            if (trainer == null) return;

            String message = "Вас призначено тренером нової сесії: ".concat(createdSession.getName());
            logger.info(message);

            notificationService.sendNotification(trainer, message);
        }
        catch (Exception ex) {
            logger.warn("NotifyTrainer failed: {}", ex.getMessage());
        }
    }


    private String extractTrainerEmail(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof SessionDto dto) {

                return userRepository.findById(dto.getTrainerId())
                        .map(User::getEmail)
                        .orElse(null);
            }
        }
        return null;
    }
}
