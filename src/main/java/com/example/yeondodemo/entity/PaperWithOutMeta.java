package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaperWithOutMeta {
    private String paperId;
    private int cites = 3;
    private int likes = 3;
    private String conference="Default confernece";
    public PaperWithOutMeta(String id){
        this.paperId = id;
    }
    public PaperWithOutMeta(){}
    public void addLike(){
        likes +=1;
    }
    public void subLike(){
        likes -=1;
    }

}
