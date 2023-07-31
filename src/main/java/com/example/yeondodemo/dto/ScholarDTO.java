package com.example.yeondodemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ScholarDTO {
    private String conference;
    private Integer cites;
    public ScholarDTO(String conference, Integer cites){
        this.cites = cites;
        this.conference = conference;
    }
}
