package com.example.yeondodemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaperHistory {
    private Long id;
    private boolean who;
    private String content;
    private int score;
}
