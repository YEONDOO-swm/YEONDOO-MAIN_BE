package com.example.yeondodemo.filter;

import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
            if(!provider.validateToken(jwt)){
                login.remove(jwt);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
