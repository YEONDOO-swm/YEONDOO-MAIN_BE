package com.example.yeondodemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BufferUpdateDTO {
    private Boolean ishit;
    private Date uddate;
    public BufferUpdateDTO(Boolean ishit, Date uddate){
        this.ishit = ishit;
        this.uddate = uddate;
    }
}
