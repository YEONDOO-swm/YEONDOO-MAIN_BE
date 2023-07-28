package com.example.yeondodemo.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Slf4j
public class IntegrationValidator {
    public static ResponseEntity<Object> inValidPaperUserRequest(String paperid, String username) {
    if((!PaperValidator.isValidPaper(paperid))){
        log.info("Invalid Paper : {}", paperid);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else if(LoginValidator.isNotValidName(username)){
        log.info("Invalid User : {}", username);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return null;
}
    public static ResponseEntity<Object> inValidPaperUserRequest(String paperid, String username, BindingResult bindingResult) {
        if((!PaperValidator.isValidPaper(paperid) || bindingResult.hasErrors())){
            log.info("Invalid Paper : {}", paperid);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else if(LoginValidator.isNotValidName(username)){
            log.info("Invalid User : {}", username);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return null;
    }
}
