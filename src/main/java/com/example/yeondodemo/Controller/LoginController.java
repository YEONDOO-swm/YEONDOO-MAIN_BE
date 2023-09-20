package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.login.AuthenticationDTO;
import com.example.yeondodemo.dto.login.GoogleInfoResponse;
import com.example.yeondodemo.dto.login.GoogleRequest;
import com.example.yeondodemo.dto.login.GoogleResponse;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.service.login.TokenType;
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
    private final RealUserRepository realUserRepository;
    private final LoginService loginService;
    private final JwtTokenProvider provider;
    @Value("${jwt.secret}")
    String jwtSecret;

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Gauth") String jwt){
        if(WorkspaceValidator.logout(jwt)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login/google")
    public ResponseEntity getGoogleInfo(@RequestBody Map<String,String> M){
        return loginService.googleLogin(M.get("authCode"));
    }
    @GetMapping("/test")
    public ResponseEntity testJoin(){
        HttpHeaders headers = loginService.test("syleelsw@snu.ac.kr");
        return new ResponseEntity(headers,HttpStatus.OK);
    }

    @GetMapping("/test/login")
    public ResponseEntity testLogin(@RequestHeader("Gauth") String jwt, @RequestParam String email){
        Set<Long> userWorkspace = realUserRepository.findByName(email);
        WorkspaceValidator.login.put(jwt, userWorkspace);
        return new ResponseEntity(HttpStatus.OK);
    }

}
