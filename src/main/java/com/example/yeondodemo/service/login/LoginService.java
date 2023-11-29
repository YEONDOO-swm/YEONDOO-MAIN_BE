package com.example.yeondodemo.service.login;

import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.entity.RefreshShort;
import com.example.yeondodemo.repository.etc.RefreshShortRedisRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.ReturnUtils;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


@Service @Slf4j  @RequiredArgsConstructor
public class LoginService {
    @Value("${jwt.secret}")
    String jwtSecret;
    private final RealUserRepository realUserRepository;
    private final JwtTokenProvider provider;
    private final ValidationService validationService;
    private final WorkspaceValidator workspaceValidator;
    private final RefreshShortRedisRepository refreshShortRedisRepository;
    private final PaperRepository paperRepository;
    public ResponseEntity testShort(String jwt){
        if(refreshShortRedisRepository.findById(jwt).isEmpty()){
            log.info("not here");
            refreshShortRedisRepository.save(new RefreshShort(jwt,"1", "1"));
        }else{
            log.info("{}", refreshShortRedisRepository.findById(jwt));
            log.info("here");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity updateRefreshToken(String jwt){
        String email = provider.getUserName(jwt);
        log.info("Expried Token.. , email: {}", email);

        if(validationService.checkRefreshToken(jwt, email)){
            log.info("Valid Expried Token.., email: {}", email);
            String refresh = validationService.makeRefreshTokenAndSaveToRedis(email);
            HttpHeaders jwtHeaders = validationService.getJwtHeaders(email, refresh);
            refreshShortRedisRepository.save(new RefreshShort(jwt, refresh, jwtHeaders.getFirst("Gauth")));
            return new ResponseEntity<>(jwtHeaders, HttpStatus.OK);
        }else{
            Optional<RefreshShort> byId = refreshShortRedisRepository.findById(jwt);
            if(byId.isPresent()){
                return new ResponseEntity<>(validationService.makeJwtHeaders(byId.get().getAccess(), byId.get().getNewRefresh()), HttpStatus.OK);
            }
            validationService.deleteRedisRepository(email);
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    public HttpHeaders test(String email){
        String refresh = validationService.makeRefreshTokenAndSaveToRedis(email);
        return validationService.getJwtHeaders(email, refresh);
    }

    @Transactional
    public ResponseEntity googleLogin(String authCode){
        RestTemplate restTemplate = new RestTemplate();
        //트라이 익셉션. 
        ResponseEntity<GoogleInfoResponse> infoResponse = validationService.getResponseFromGoogle(authCode, restTemplate);
        
        String email= infoResponse.getBody().getEmail();
        String name = infoResponse.getBody().getName();

        if(realUserRepository.exist(email)==null) {join(email);}
        int leftQuestionsById = paperRepository.findLeftQuestionsById(email);

        String refresh = validationService.makeRefreshTokenAndSaveToRedis(email);
        return new ResponseEntity<>(Map.of("username", name, "leftQuestions", leftQuestionsById), validationService.getJwtHeaders(email, refresh), HttpStatus.OK);
    }

    public void join(String email){
        log.info("Join {}", email);
        realUserRepository.save(email);
    }

    public ResponseEntity setLimit(Integer token) {
        log.info("Setting tokenLimit to {}", token);
        paperRepository.setToken(token);
        return new ResponseEntity(HttpStatus.OK);
    }
}
