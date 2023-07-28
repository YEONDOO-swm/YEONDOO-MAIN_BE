package com.example.yeondodemo.dto.paper;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
public class PaperContainerDTO {
    private List<Paper4Container> papers;
    public PaperContainerDTO(List<Paper4Container> papers){
        this.papers = papers;
    }
}
