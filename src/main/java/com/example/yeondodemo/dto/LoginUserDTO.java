package com.example.yeondodemo.dto;
import com.example.yeondodemo.validation.UserValidator;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @ToString @Slf4j
public class LoginUserDTO {
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{6,19}$")
    private String username;
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9!@#$%^&*()]{6,19}$")
    private String password;
    @AssertTrue
    public boolean isValidUsername(){
        log.info("isValid? {} ", UserValidator.isValid(username, password) );
        return UserValidator.isValid(username, password);
    }
}
