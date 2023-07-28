package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter @ToString
public class    Paper extends PaperWithOutMeta{
    private String paperId;
    private String title="Default title";
    private String conference="Default confernece";
    private int cites = 3;
    private String url;
    private String abs = "Default Abastract";
    private String summary = "Default Abastract";
    private List<String> Authors = new ArrayList<>(Arrays.asList("default1","default2"));
    private int likes=3;
    private int year=2023;
    public Paper(String id){
        this.paperId = id;
        this.url = "http://arxiv.org/abs/"+paperId;
    }

    public void addLike(){
        likes +=1;
    }
    public void subLike(){
        likes -=1;
    }

}
