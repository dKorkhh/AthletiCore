package com.example.athleticore.aspect;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandlingAspect {
    private static final Logger logger = LogManager.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(
            pointcut = "within(@org.springframework.stereotype.Service *) || " + "within(@org.springframework.web.bind.annotation.RestController *)",
            throwing = "ex"
    )
    public void handleException(Exception ex) {
        logger.error("Error fetching: {}", ex.getMessage());
    }
}
