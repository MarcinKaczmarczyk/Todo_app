package io.github.MarcinK.todoapp.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicAspect {
    private static final Logger logger= LoggerFactory.getLogger(LogicAspect.class);
    private final Timer projectCreateGroupTimer;

    public LogicAspect(MeterRegistry registry) {
        this.projectCreateGroupTimer = registry.timer("logic.product.create.group");
    }
    @Pointcut("execution(* io.github.MarcinK.todoapp.logic.ProjectService.createGroup(..))")
    void projectServiceCreateGroup(){

    }

   // @Before("execution(* io.github.MarcinK.todoapp.logic.ProjectService.createGroup(..))")
    @Before("projectServiceCreateGroup()")
    void logMethodCall(JoinPoint jp){
        logger.info("Before {} with {}",jp.getSignature().getName(), jp.getArgs());
    }

   // @Around("execution(* io.github.MarcinK.todoapp.logic.ProjectService.createGroup(..))")
    @Around("projectServiceCreateGroup()")
    Object aroundProjectCreateGroup(ProceedingJoinPoint jp) {
        return projectCreateGroupTimer.record(() -> {
            try {
                return jp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

    }
}
