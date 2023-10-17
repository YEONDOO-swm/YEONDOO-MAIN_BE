package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.PaperAnswerResponseDTO;
import com.example.yeondodemo.dto.paper.item.ItemPosition;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QueryHistory {
    private Long id;
    private Long workspaceId;
    private String paperid;
    private String content;
    private Long idx;
    private boolean who;
    private String positionString;
    private String context;
    private String extraPaperId;

    private Float totalTokens;
    private Float promptTokens;
    private Float completionTokens;
    private Float totalCost;

    public QueryHistory(Long workspaceId, String paperid, long idx, boolean who, QuestionDTO content){
        this.workspaceId = workspaceId;
        this.paperid = paperid;
        this.idx = idx;
        this.content = content.getQuestion();
        this.who = who;
        this.context = content.getContext();
        for (String paperId : content.getPaperIds()) {
            if(!paperId.equals(paperid)){
                this.extraPaperId = paperId;
                break;
            }
        }
    }
    public QueryHistory(Long workspaceId, String paperid, long idx, boolean who, PaperAnswerResponseDTO response){
        this.workspaceId = workspaceId;
        this.paperid = paperid;
        this.idx = idx;
        this.content = response.getAnswer();
        this.who = who;
        this.positionString = response.getPosition().toString();
/*
        this.totalCost = response.getTrack().getTotalCost();
        this.promptTokens = response.getTrack().getPromptTokens();
        this.completionTokens = response.getTrack().getCompletionTokens();*/
        //this.totalTokens = response.getTrack().getTotalTokens();
    }
}
