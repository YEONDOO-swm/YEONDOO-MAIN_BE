package com.example.yeondodemo.service.login;

import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.dto.login.GoogleRequest;
import com.example.yeondodemo.dto.login.GoogleResponse;
import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.filter.Timer;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
@Service @RequiredArgsConstructor @Slf4j
public class ValidationService {
    private final RealUserRepository realUserRepository;
    private final JwtTokenProvider provider;
    @Autowired
    private final RefreshRedisRepository refreshRedisRepository;
    @Value("${spring.google.client_id}")
    String clientId;
    @Value("${spring.google.client_secret}")
    String clientSecret;
    @Timer("Google Authcode")
    public ResponseEntity<GoogleInfoResponse> getResponseFromGoogle(String authCode, RestTemplate restTemplate){
        log.info("authcode: {}", authCode);
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri("postmessage")
                .grantType("authorization_code").build();

        ResponseEntity<GoogleResponse> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken = response.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        return restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfoResponse.class);
    }
    @Timer("Checking RefreshToken")
    public boolean checkRefreshToken(String jwt,String key){
        Optional<RefreshEntity> refreshEntity = refreshRedisRepository.findById(key);
        if(refreshEntity.isPresent()){
            log.info("refresh Token data... {}", refreshEntity.get().toString());
            RefreshEntity data = refreshEntity.get();
            if(!data.getRefreshToken().equals(jwt) || data.getExpired() > System.currentTimeMillis()){
                refreshRedisRepository.deleteById(key);
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    @Timer("saving to Redis")
    public String makeRefreshTokenAndSaveToRedis(String email) {
        String refreshToken = provider.createJwt(email, TokenType.REFRESH);
        RefreshEntity refreshEntity = new RefreshEntity(email, refreshToken);
        refreshRedisRepository.save(refreshEntity);
        return refreshToken;
    }
     @Timer("getHeadersFromDB")
     public HttpHeaders getJwtHeaders(String email, String refreshToken){
        String accessToken = provider.createJwt(email, TokenType.ACCESS);
        Set<Long> userWorkspace = realUserRepository.findByName(email);

        WorkspaceValidator.addLogin(accessToken, userWorkspace);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Gauth", accessToken);
        headers.add("RefreshToken", refreshToken);
        return headers;
    }
}
