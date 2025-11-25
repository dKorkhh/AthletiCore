package com.example.athleticore.aspect;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandlingAspect {
    private static final Logger logger = LogManager.getLogger(ExceptionHandlingAspect.class);

    @Around("execution(* com.example..*(..)) && !within(com.example.athleticore.security.JwtFilter)")
    public Object catchAll(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            logger.error("Error fetching: {}", ex.getMessage(), ex);

            throw ex;
        }
    }
}
