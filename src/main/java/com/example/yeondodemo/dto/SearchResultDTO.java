package com.example.yeondodemo.dto;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class SearchResultDTO {
    private String answer;
    private List<PaperDTO> papers = new ArrayList<>();
}
