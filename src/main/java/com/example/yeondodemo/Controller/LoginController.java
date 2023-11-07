package com.example.yeondodemo.Controller;

import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/api")
public class LoginController {
    private final RealUserRepository realUserRepository;
    private final LoginService loginService;
    private final JwtTokenProvider provider;
    private final WorkspaceValidator workspaceValidator;
    @Value("${jwt.secret}")
    String jwtSecret;

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Gauth") String jwt){
        workspaceValidator.logout(jwt);
        return new ResponseEntity(HttpStatus.OK);
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
    @GetMapping("/test2")
    public ResponseEntity testJoin2(){
        HttpHeaders headers = loginService.test("syleelsw@gmail.com");
        return new ResponseEntity(headers,HttpStatus.OK);
    }


    @GetMapping("/test/login")
    public ResponseEntity testLogin(@RequestHeader("Gauth") String jwt, @RequestParam String email){
        Set<Long> userWorkspace = realUserRepository.findByName(email);
        workspaceValidator.login.put(jwt, userWorkspace);
        return new ResponseEntity(HttpStatus.OK);
    }

}
