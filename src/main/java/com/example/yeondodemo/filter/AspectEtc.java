package com.example.yeondodemo.filter;

import com.example.yeondodemo.repository.paper.batis.BatisRecentlyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor
@Aspect
@Component
@Slf4j
public class AspectEtc {
    private final BatisRecentlyRepository recentlyRepository;
    @Pointcut("execution(* com.example.yeondodemo.service..*(..))")
    public void servicePointcut(){}
    @Around("servicePointcut() && @annotation(timer)")
    public Object doTimer(ProceedingJoinPoint joinPoint, Timer timer) throws  Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // joinPoint.proceed() -> 실질적인 method 실행
        Object result = joinPoint.proceed();

        // 실행 후
        stopWatch.stop();
        // 총 걸린 시간 (초단위)
        log.info("{} total time : {}",timer.value(), + stopWatch.getTotalTimeSeconds());
        return result;
    }

    @AfterReturning(pointcut= "servicePointcut() && args(paperId, workspaceId) && @annotation(ReadPaper)")
    public void doService(String paperId, Long workspaceId) throws Throwable{
        recentlyRepository.save(paperId, workspaceId);
    }
}
