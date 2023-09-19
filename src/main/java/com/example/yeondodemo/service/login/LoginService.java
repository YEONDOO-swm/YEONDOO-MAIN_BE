package com.example.yeondodemo.service.login;

import com.example.yeondodemo.dto.login.AuthenticationDTO;
import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.dto.login.GoogleRequest;
import com.example.yeondodemo.dto.login.GoogleResponse;
import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.KeywordRepository;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.Authenticator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service @Slf4j  @RequiredArgsConstructor
public class LoginService {
    @Value("${spring.google.client_id}")
    String clientId;
    @Value("${spring.google.client_secret}")
    String clientSecret;
    @Value("${jwt.secret}")
    String jwtSecret;
    private final RealUserRepository realUserRepository;
    private final JwtTokenProvider provider;
    @Autowired
    private final RefreshRedisRepository refreshRedisRepository;
    @Transactional
    public ResponseEntity updateRefreshToken(String jwt){
        log.info("Expried Token..");
        String email = provider.getUserName(jwt);
        if(checkRefreshToken(jwt, email)){
            return new ResponseEntity<>(setDefaultLoginSetting(email), HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    public boolean checkRefreshToken(String jwt,String key){
        Optional<RefreshEntity> refreshEntity = refreshRedisRepository.findById(key);
        if(refreshEntity.isPresent()){
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
    public String getJwtFromGoogle(String authCode, RestTemplate restTemplate){
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri("postmessage")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        return response.getBody().getId_token();
    }
    public HttpHeaders setDefaultLoginSetting(String email){
        String jwt = provider.createJwt(email, TokenType.ACCESS);
        String refreshToken = provider.createJwt(email, TokenType.REFRESH);
        RefreshEntity refreshEntity = new RefreshEntity(email,refreshToken);
        refreshRedisRepository.save(refreshEntity);
        Set<Long> userWorkspace = realUserRepository.findByName(email);
        WorkspaceValidator.addLogin(jwt, userWorkspace);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Gauth", jwt);
        headers.add("RefreshToken", refreshToken);
        log.info("User {}'s workspaces: {}", email, userWorkspace);
        return headers;
    }
    @Transactional
    public ResponseEntity googleLogin(String authCode){
        RestTemplate restTemplate = new RestTemplate();
        String jwtToken=getJwtFromGoogle(authCode, restTemplate);
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfoResponse> infoResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfoResponse.class);
        String email=infoResponse.getBody().getEmail();
        String name = infoResponse.getBody().getName();
        if(realUserRepository.exist(email)==null){
            realUserRepository.save(email);
        }
        Map ret = new HashMap<String, String>();
        ret.put("username", name);
        return new ResponseEntity<>(ret, setDefaultLoginSetting(email), HttpStatus.OK);
    }

}
