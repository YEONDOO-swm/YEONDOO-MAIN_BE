package com.example.yeondodemo.dto;

import com.example.yeondodemo.validation.PaperValidator;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter @Setter @ToString @Slf4j
public class QuestionDTO {
        @Size(min=1, max = 300)
        private String question;
        private List<String> paperIds;
        private String context;
        private Integer paperIndex;
        @AssertTrue(message = "invalid paperId")
        public boolean isValidPaperId(){
            for (String paperId : paperIds) {
                if(!PaperValidator.isValidPaper(paperId)){
                    log.info("{} fail..", paperId);
                    return false;
                }
                log.info("{} pass..", paperId);
            }
            return true;
        }
    }