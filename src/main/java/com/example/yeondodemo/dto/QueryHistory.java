package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QueryHistory {
    private Long id;
    private String username;
    private String paperid;
    private String content;
    private Integer idx;
    private boolean who;

    private Float totalTokens;
    private Float promptTokens;
    private Float completionTokens;
    private Float totalCost;

    public QueryHistory(String username, String paperid, int idx, boolean who, String content){
        this.username = username;
        this.paperid = paperid;
        this.idx = idx;
        this.content = content;
        this.who = who;
    }
    public QueryHistory(String username, String paperid, int idx, boolean who, PythonQuestionResponse response){
        this.username = username;
        this.paperid = paperid;
        this.idx = idx;
        this.content = response.getAnswer();
        this.who = who;

        this.totalCost = response.getTrack().getTotalCost();
        this.promptTokens = response.getTrack().getPromptTokens();
        this.completionTokens = response.getTrack().getCompletionTokens();
        this.totalTokens = response.getTrack().getTotalTokens();
    }
}
