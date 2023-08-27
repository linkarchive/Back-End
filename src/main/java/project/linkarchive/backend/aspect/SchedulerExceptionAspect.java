package project.linkarchive.backend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SchedulerExceptionAspect {

    @AfterThrowing(pointcut = "execution(* project.linkarchive.backend..*.*(..)) && @annotation(org.springframework.scheduling.annotation.Scheduled)", throwing = "ex")
    public void handleSchedulerException(RuntimeException ex) {
        log.error("스케쥴링된 작업 실행 중 오류 발생: {}", ex.getMessage(), ex);
    }

}