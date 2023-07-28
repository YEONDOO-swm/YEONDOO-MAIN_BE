package com.example.yeondodemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QueryHistory {
    private int id;
    private String username;
    private String paperid;
    private String content;
    private int idx;
    private boolean who;
    public QueryHistory(String username, String paperid, int idx, boolean who, String content){
        this.username = username;
        this.paperid = paperid;
        this.idx = idx;
        this.content = content;
        this.who = who;
    }
}
