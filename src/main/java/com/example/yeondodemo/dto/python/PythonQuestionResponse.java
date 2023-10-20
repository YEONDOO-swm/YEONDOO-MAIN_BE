package com.example.yeondodemo.dto.python;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PythonQuestionResponse {
    private List<PythonPaperPosition> coordinates;
}
