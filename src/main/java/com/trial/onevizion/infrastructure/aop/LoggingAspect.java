package com.trial.onevizion.infrastructure.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.trial.onevizion.application.service..*) || within(com.trial.onevizion.infrastructure.repository..*)")
    public void applicationPackagePointcut() {
    }

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            log.info("Entering in Method :  {}", joinPoint.getSignature().getName());
            log.info("Class Name :  {}", joinPoint.getSignature().getDeclaringTypeName());
            log.info("Arguments :  {}", Arrays.toString(joinPoint.getArgs()));
            log.info("Target class : {}", joinPoint.getTarget().getClass().getName());
        }
    }

    @After("applicationPackagePointcut()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Leaving Method :  {}", joinPoint.getSignature().getName());
    }
}
