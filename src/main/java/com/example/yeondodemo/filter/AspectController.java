package com.example.yeondodemo.filter;

import com.example.yeondodemo.entity.User;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Aspect @Slf4j  @Component
public class AspectController {
    private final JwtTokenProvider provider;
    private Map login = UserValidator.login;
    @Around("com.example.yeondodemo.filter.PointCuts.allController() && args(jwt,username,..)")
    public ResponseEntity doFilter(ProceedingJoinPoint joinPoint, String jwt, String username) throws Throwable {
        ResponseEntity result;
        String name;
        try{
             name = provider.getUserName(jwt);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(provider.validateToken(jwt) && name == username){
             result = (ResponseEntity) joinPoint.proceed();
             log.info("valid user: {}", username);
             jwt = provider.createJwt(name);
             HttpHeaders headers = new HttpHeaders();
             headers.add("X_AUTH_TOKEN", jwt);
             HttpStatusCode status = result.getStatusCode();
             return new ResponseEntity<>(result.getBody(), headers, status);
        }else{
            if(login.get(name)!=null){
                login.remove(name);
            }
            result = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return result;
    }
}
