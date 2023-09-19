package com.example.yeondodemo.Controller;

import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.filter.RefreshJwtValidation;
import com.example.yeondodemo.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/update") @RequiredArgsConstructor
@Slf4j
public class UpdateController {
    private final LoginService loginService;
    @GetMapping("/token") @RefreshJwtValidation
    public ResponseEntity updateToken(@RequestHeader("RefreshToken") String jwt){
        return loginService.updateRefreshToken(jwt);
    }
}
