package com.javalabs.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class MethodLoggerAspect {

    @Before("execution(* com.javalabs.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.javalabs.*.*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }
}