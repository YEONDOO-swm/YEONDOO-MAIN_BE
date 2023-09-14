package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.dto.login.GoogleRequest;
import com.example.yeondodemo.dto.login.GoogleResponse;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.LoginUtil;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/api")
public class LoginController {
    private final UserRepository userRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final RealUserRepository realUserRepository;
    private final LoginService loginService;
    private final JwtTokenProvider provider;
    @Value("${spring.google.client_id}")
    String clientId;
    @Value("${spring.google.client_secret}")
    String clientSecret;
    @Value("${jwt.secret}")
    String jwtSecret;
    @PostMapping("/login/google")
    public ResponseEntity getGoogleInfo(@RequestBody Map<String,String> M){
        String authCode = M.get("authCode");
        System.out.println(authCode);
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri("postmessage")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken=response.getBody().getId_token();
        Map<String, String> map=new ConcurrentHashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfoResponse> infoResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfoResponse.class);
        String email=infoResponse.getBody().getEmail();
        String name = infoResponse.getBody().getName();
        String jwt = provider.createJwt(email);
        log.info("jwt: {}", jwt);
        if(realUserRepository.exist(email)==null){
            realUserRepository.save(email);
        }
        Set<Long> userWorkspace = realUserRepository.findByName(email);
        WorkspaceValidator.addLogin(jwt, userWorkspace);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Gauth", jwt);
        Map ret = new HashMap<String, String>();
        ret.put("username", name);
        ret.put("Gauth", jwt);
        return new ResponseEntity(ret, headers,HttpStatus.OK);
    }
    @GetMapping("/test")
    public ResponseEntity testJoin(){
        String jwt = provider.createJwt("syleelsw@snu.ac.kr");
        HttpHeaders headers = new HttpHeaders();
        String name = provider.getUserName(jwt);

        log.info("jwt: {}", jwt);
        if(realUserRepository.exist(name)==null){
            realUserRepository.save(name);
        }
        Set<Long> userWorkspace = realUserRepository.findByName(name);
        WorkspaceValidator.login.put(jwt, userWorkspace);
        headers.set("Gauth", jwt);
        System.out.println("userWorkspace = " + userWorkspace);
        System.out.println(userWorkspace.getClass());
        return new ResponseEntity(headers,HttpStatus.OK);
    }

    @GetMapping("/test/login")
    public ResponseEntity testLogin(@RequestHeader("Gauth") String jwt, @RequestParam String email){
        Set<Long> userWorkspace = realUserRepository.findByName(email);
        WorkspaceValidator.login.put(jwt, userWorkspace);
        return new ResponseEntity(HttpStatus.OK);
    }

}
