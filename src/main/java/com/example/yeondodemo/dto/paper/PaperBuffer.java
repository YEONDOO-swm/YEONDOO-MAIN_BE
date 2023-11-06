package com.example.yeondodemo.dto.paper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class PaperBuffer {
    private String id;
    private Boolean ishit=false;
    private Date uddate;
    public PaperBuffer(String id){
        this.id = id;
        this.uddate = new Date();
    }
    public PaperBuffer(String id, boolean ishit){
        this.id = id;
        this.ishit = ishit;
        this.uddate = new Date();
    }
}
