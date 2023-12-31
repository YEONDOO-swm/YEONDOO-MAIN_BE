package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.validation.PaperValidator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @ToString @Slf4j @JsonAutoDetect
public class LikeOnOffDTO {
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
