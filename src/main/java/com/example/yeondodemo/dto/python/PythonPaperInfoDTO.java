package com.example.yeondodemo.dto.python;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PythonPaperInfoDTO{
    private String summary;
    private List<String> insights;
    private List<String> questions;
    private List<String> subjectRecommends;
    private List<String> references;
}
