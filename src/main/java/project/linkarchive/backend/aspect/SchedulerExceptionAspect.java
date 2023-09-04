package project.linkarchive.backend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SchedulerExceptionAspect {

    @Pointcut("execution(* project.linkarchive.backend..*.*(..))")
    public void inBackendPackage() {
    }

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAnnotation() {
    }

    @AfterThrowing(pointcut = "inBackendPackage() && scheduledAnnotation()", throwing = "ex")
    public void handleSchedulerException(JoinPoint joinPoint, Exception ex) {
        log.error("An error occurred while executing scheduled task: {} in method: {}", ex.getMessage(), joinPoint.getSignature().toShortString(), ex);
    }

}