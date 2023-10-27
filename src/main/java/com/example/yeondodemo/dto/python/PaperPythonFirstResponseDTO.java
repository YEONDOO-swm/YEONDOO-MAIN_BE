package com.example.yeondodemo.dto.python;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PaperPythonFirstResponseDTO {
    private String summary;
    private List<String> questions;
    private float embeddingTokens;
    private List<String> references;
}
