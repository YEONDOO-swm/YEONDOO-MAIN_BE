package com.example.yeondodemo.dto.python;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PythonQuestionResponse {
    private String answer;
    private PythonPaperPosition coordinates;
}
