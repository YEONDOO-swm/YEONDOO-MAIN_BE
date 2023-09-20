package com.example.yeondodemo.filter;

import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.opensearch.cluster.ClusterState;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Map;

@RequiredArgsConstructor
@Aspect @Component @Slf4j
public class AspectController {
    private final JwtTokenProvider provider;
    private Map login = WorkspaceValidator.login;
    @Around("com.example.yeondodemo.filter.PointCuts.allController() && args(jwt,workspaceId,..)")
    public ResponseEntity<?> doFilter(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        log.info("AOPAOP");
        if(provider.validateToken(jwt) && WorkspaceValidator.isValid(jwt, workspaceId)){
            return (ResponseEntity<?>) joinPoint.proceed();
        }else{
            if((!provider.validateToken(jwt))&& WorkspaceValidator.isValid(jwt, workspaceId)){
                login.remove(jwt);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Around("com.example.yeondodemo.filter.PointCuts.targetWorkspaceAdd() && args(jwt,workspaceId,..)")
    public Object doFilter3(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        log.info("AOPAOP22");
        if(workspaceId==null){
            if(provider.validateToken(jwt)){
                return joinPoint.proceed();
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            if(provider.validateToken(jwt) && WorkspaceValidator.isValid(jwt, workspaceId)){
                return (ResponseEntity<?>) joinPoint.proceed();
            }else{
                if((!provider.validateToken(jwt))&& WorkspaceValidator.isValid(jwt, workspaceId)){
                    login.remove(jwt);
                }
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }
    @Around("@annotation(RefreshJwtValidation) && args(jwt,..)")
    public Object doFilterRefresh(ProceedingJoinPoint joinPoint, String jwt) throws Throwable {
        if(provider.validateToken(jwt)){
            return joinPoint.proceed();
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Around("@annotation(JwtValidation) && args(jwt,..)" )
    public Object doFilter1(ProceedingJoinPoint joinPoint, String jwt) throws Throwable {
        if(provider.validateToken(jwt)&&(WorkspaceValidator.login.get(jwt)!=null)){
            return joinPoint.proceed();
        }else{
            if((!provider.validateToken(jwt))&& (WorkspaceValidator.login.get(jwt)!=null)){
                login.remove(jwt);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @Around("@annotation(timer)")
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

}
