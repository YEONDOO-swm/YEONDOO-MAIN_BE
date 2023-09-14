package com.example.yeondodemo.dto;

import com.example.yeondodemo.validation.PaperValidator;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Getter @Setter @ToString @Slf4j
public class LikeOnOffDTO {
    @Value("workspaceId")
    private Long workspaceId;
    private String paperId;
    private boolean on;
    @AssertTrue
    public boolean isValidPaperId(){
        return PaperValidator.isValidPaper(paperId);
    }
    @AssertTrue
    public boolean isValidOnOff(){
        return PaperValidator.isValidOnOff(workspaceId, paperId, on);
    }
}
