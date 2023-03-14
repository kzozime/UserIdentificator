package com.otsa.useridentificator.aspect;

import com.otsa.useridentificator.controller.UserController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Around("execution(public * com.otsa.useridentificator.controller.UserController.*(..))")
    public Object logSuperviser(ProceedingJoinPoint joinPoint)
            throws Throwable {
        String startMessage = switch (joinPoint.getSignature().getName()) {
            case "getUsers" -> "Listing users :";
            case "getUser" -> "Get user with id " + joinPoint.getArgs()[0];
            case "postUser" -> "Register new user";
            default -> "";
        };

        LOGGER.info(startMessage);
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            LOGGER.error("Request failed!\n message : " + e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            long processingTime = end - start;
            LOGGER.info("Request succed in " + processingTime + " milliseconds");
        }
        return null;
    }

}