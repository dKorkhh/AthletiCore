package com.example.athleticore.aspect;

import com.example.athleticore.exception.limit.LimitCallsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Aspect
@Component
public class RateLimitAspect {
    private final Map<String, Deque<Long>> userCalls = new ConcurrentHashMap<>();

    private static final int LIMIT = 10;
    private static final long TIME_WINDOW = 60_000L;
    private static final String ANONYMOUS_USER = "anonymous";

    @Around("@annotation(com.example.athleticore.utils.validation.LimitCallMethod)")
    public Object applyRateLimit(ProceedingJoinPoint pjp) throws Throwable {
        String email = getCurrentUserEmail();
        if (email == null) {
            email = ANONYMOUS_USER;
        }
        
        long now = System.currentTimeMillis();

        userCalls.putIfAbsent(email, new ConcurrentLinkedDeque<>());
        Deque<Long> calls = userCalls.get(email);

        calls.removeIf(t -> now - t > TIME_WINDOW);

        if (calls.size() >= LIMIT) {
            throw new LimitCallsException("Перевищено ліміт запитів для користувача: " + email);
        }

        calls.addLast(now);
        return pjp.proceed();
    }

    private String getCurrentUserEmail() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return null;
        }
    }
}
