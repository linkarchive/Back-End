package project.linkarchive.backend.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SchedulerExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerExceptionAspect.class);

    @AfterThrowing(pointcut = "execution(* project.linkarchive.backend..*.*(..)) && @annotation(org.springframework.scheduling.annotation.Scheduled)", throwing = "ex")
    public void handleSchedulerException(RuntimeException ex) {
        logger.error("스케쥴링된 작업 실행 중 오류 발생: {}", ex.getMessage(), ex);
    }

}