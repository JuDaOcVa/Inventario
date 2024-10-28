package com.judaocva.inventariocore.controllers;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Log4j2
public class LoggingAspectController {

    @Before("execution(*  com.judaocva.inventariocore..*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        log.info("{{LOG}} Before method execute: {}", joinPoint.getSignature().toShortString());
        if (Arrays.stream(joinPoint.getArgs()).anyMatch(Objects::nonNull))
            log.info("{{LOG}} Before method execute: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(*  com.judaocva.inventariocore..*.*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        log.info("{{LOG}} After method execute: {}", joinPoint.getSignature().toShortString());
        if (Arrays.stream(joinPoint.getArgs()).anyMatch(Objects::nonNull))
            log.info("{{LOG}} After method execute: {}", Arrays.toString(joinPoint.getArgs()));
        log.info("{{LOG}} Result : {}", result);
    }
}
