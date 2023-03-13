package com.bikatoo.lancer.event.core;

import com.bikatoo.lancer.event.persistence.EventTriggerRecordManager;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AsyncAspect {

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private EventTriggerRecordManager eventTriggerRecordManager;

    @Around("execution( void com.bikatoo.lancer.*.event.Async*Handler.*(..))")
    public Object async(ProceedingJoinPoint joinPoint) {
        executor.execute(() -> {
            try {
                joinPoint.proceed();
            } catch (Throwable e) {
                log.error("proceed error", e);
            }
        });

        executor.execute(() -> {
            Object event = joinPoint.getArgs()[0];
            eventTriggerRecordManager.addRecord((Event) event);
        });
        log.info("===============Async event [{}] ======================= execute", joinPoint.getSignature().getName());
        return null;
    }

}
