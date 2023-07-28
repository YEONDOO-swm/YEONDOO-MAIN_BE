package com.example.yeondodemo.dto;

import com.example.yeondodemo.validation.LoginValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@ToString
@Slf4j
public class UserProfileDTO {
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{6,19}$")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9]{1,30}")
    private String studyField;
    @Size(min=1,max=3)
    private List<String> keywords;
    @AssertTrue
    public boolean isAppropriateKeyword(){
        for (String keyword : keywords) {
            if(!keyword.matches("^[A-Za-z0-9]{1,30}")){
                return false;
            }
        }
        return true;
    }
    @AssertFalse
    public boolean isNotValidUsername(){
        return LoginValidator.isNotValidName(username);
    }
}
