package com.example.yeondodemo.dto;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class SearchResultDTO {
    private String query;
    private String answer;
    private Long id;
    private List<PaperDTO> papers = new ArrayList<>();
    public SearchResultDTO(String query){
        this.query = query;
    }
    public SearchResultDTO(String query, String answer, Long id, List<PaperDTO> papers){
        this.id = id;
        this.query = query;
        this.answer =answer;
        this.papers = papers;
    }
}
