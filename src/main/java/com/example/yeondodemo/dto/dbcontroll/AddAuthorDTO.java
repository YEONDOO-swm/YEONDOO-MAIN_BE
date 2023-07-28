package com.example.yeondodemo.dto.dbcontroll;

import com.example.yeondodemo.validation.PaperValidator;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class AddAuthorDTO {
    private String paperId;
    @NotNull
    private List<String> authors;
    @AssertTrue
    public boolean isValidPaperId(){
        return PaperValidator.isValidPaper(paperId);
    }
}
