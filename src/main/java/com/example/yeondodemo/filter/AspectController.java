package com.example.yeondodemo.filter;

import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.Map;

@RequiredArgsConstructor
@Aspect @Component @Slf4j
public class AspectController {
    private final JwtTokenProvider provider;
    private final UserRepository workspaceRepository;
    private final WorkspaceValidator workspaceValidator;
    private final PaperRepository paperRepository;
    @AfterReturning("com.example.yeondodemo.filter.PointCuts.allController() && args(jwt, workspaceId, ..)" )
    @Order(value = 4)
    public void updateEditDate(String jwt, Long workspaceId) throws  Throwable{
        log.info("Update workspaceId AOP");
        workspaceRepository.updateDate(workspaceId);
    }
    @Before("com.example.yeondodemo.filter.PointCuts.allController() && @annotation(ItemSetting) && args(jwt,workspaceId,paperId,paperItem,..)")
    @Order(value = 3)
    public void settingItem(String jwt, Long workspaceId, String paperId, ItemAnnotation paperItem) throws Throwable {
        log.info("Aop: setting item");
        paperItem.setPaperId(paperId);
        paperItem.setWorkspaceId(workspaceId);
        paperItem.setPositionString(paperItem.getPosition().toString());
        log.info("Item complete: {}", paperItem);
    }

    @Around("com.example.yeondodemo.filter.PointCuts.allController() && args(jwt,workspaceId,..)")
    @Order(value = 1)
    public ResponseEntity<?> doFilter(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        log.info("AOPAOP");
        return getResponseEntity(joinPoint, jwt, workspaceId);
    }

    private ResponseEntity<?> getResponseEntity(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        if (!provider.validateToken(jwt)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!workspaceValidator.isValid(jwt, workspaceId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return (ResponseEntity<?>) joinPoint.proceed();
        }
    }

    @Around("com.example.yeondodemo.filter.PointCuts.targetWorkspaceAdd() && args(jwt,workspaceId,..)")
    @Order(value = 2)
    public Object doFilter3(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        log.info("AOPAOP22");
        if(workspaceId==null){
            if(provider.validateToken(jwt)){
                return joinPoint.proceed();
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            return getResponseEntity(joinPoint, jwt, workspaceId);
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
        if(provider.validateToken(jwt)&&(workspaceValidator.login.get(jwt)!=null)){
            log.info("workspace.login: {}", workspaceValidator.login.get(jwt));
            return joinPoint.proceed();
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @Around("execution(* com.example.yeondodemo.ControllerAsnc..*(..)) && args(jwt,workspaceId,..)")
    @Order(value = 1)
    public Flux<String> doFilterAsync(ProceedingJoinPoint joinPoint, String jwt, Long workspaceId) throws Throwable {
        log.info("AOPAOASYNC validation");
        if (!provider.validateToken(jwt)){
            return  Flux.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        }else if(!workspaceValidator.isValid(jwt, workspaceId)){
            return  Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        }else{
            String email = provider.getUserName(jwt);
            int leftQuestionsById = paperRepository.findLeftQuestionsById(email);
            if(leftQuestionsById>0){
                return (Flux<String>) joinPoint.proceed();
            }else{
                return Flux.error(new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED));
            }
        }
    }


}
