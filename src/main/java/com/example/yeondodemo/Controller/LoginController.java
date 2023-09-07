package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.dto.login.GoogleRequest;
import com.example.yeondodemo.dto.login.GoogleResponse;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import com.example.yeondodemo.entity.User;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.utils.LoginUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/api")
public class LoginController {
    private final UserRepository userRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final LoginService loginService;
    @Value("${spring.google.client_id}")
    String clientId;
    @Value("${spring.google.client_secret}")
    String clientSecret;
    @Value("${jwt.secret}")
    String jwtSecret;
    @PostMapping("/login/google")
    public ResponseEntity getGoogleInfo(@RequestBody Map<String,String> M){
        String jwt = LoginUtil.createJwt("syleelsw@snu.ac.kr", jwtSecret);
        System.out.println("jwt = " + jwt);
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
        User user = userRepository.findByName(email);
        if(user==null){
            user = new User(email, "",name);
            userRepository.save(user);
        }
        Map ret = new HashMap<String, String>();
        ret.put("jwt", jwt);
        ret.put("username", name);
        return new ResponseEntity(ret, HttpStatus.OK);
    }
    @GetMapping("/join")
    public ResponseEntity join(@RequestParam String username, @RequestParam String password){
        if( !username.matches("^[A-Za-z][A-Za-z0-9]{6,19}$") || !password.matches("^[A-Za-z][A-Za-z0-9!@#$%^&*()]{6,19}$") || (userRepository.findByName(username) != null)){
            log.info("usrnaem "+username.matches("^[A-Za-z][A-Za-z0-9]{6,19}$"));
            log.info("pw "  + password.matches("^[A-Za-z][A-Za-z0-9!@#$%^&*()]{6,19}$"));
            log.info("isin: " + userRepository.findByName(username));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        User user = new User(username, password);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors: {}", bindingResult);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("login Success, user: {}",loginUserDTO.getUsername());
        User user = loginService.join(loginUserDTO);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isFirst",user.getIsFirst());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/userprofile/{username}")
    public ResponseEntity getStudyFields(@PathVariable String username){
        HttpStatus status = HttpStatus.OK;
        if(!loginService.isValidUser(username)){status=HttpStatus.NOT_FOUND;}
        else if(loginService.checkNotFirst(username)){status=HttpStatus.UNAUTHORIZED;}
        Map<String, List<String>> response = new HashMap<>();
        response.put("fields",studyFieldRepository.findAll());
        return new ResponseEntity<>(response, status);
    }
    @PostMapping("/userprofile")
    public ResponseEntity setUserProfile(@Validated @RequestBody UserProfileDTO userProfileDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            log.info("errors: {}", bindingResult);
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println("allError = " + allError);
            }
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getCode());
            }

            switch(Objects.requireNonNull(bindingResult.getFieldError().getCode())){
                //@1Login기능 제대로 구현시 UnAuthorized추가할것.
                case "Size", "AssertTrue", "Pattern":
                    status = HttpStatus.BAD_REQUEST;
                    break;
            }
            return new ResponseEntity<>(status);
        }
        loginService.setUserProfile(userProfileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
