package com.example.yeondodemo.dto;

import com.example.yeondodemo.validation.UserValidator;
import com.example.yeondodemo.validation.PaperValidator;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @ToString @Slf4j
public class LikeOnOffDTO {
    private String username;
    private String paperId;
    private boolean on;
    @AssertFalse
    public boolean isNotValidUsername(){
        return UserValidator.isNotValidName(username);
    }
    @AssertTrue
    public boolean isValidPaperId(){
        return PaperValidator.isValidPaper(paperId);
    }
    @AssertTrue
    public boolean isValidOnOff(){
        return PaperValidator.isValidOnOff(username, paperId, on);
    }
}
