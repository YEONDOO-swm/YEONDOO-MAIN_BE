package com.example.yeondodemo.entity;

import com.example.yeondodemo.dto.SearchResultDTO;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SearchHistory {
    private String username;
    private String query;
    private String answer;
    private Integer searchType;
    public SearchHistory(SearchResultDTO searchResultDTO, String username,  Integer searchType){
        this.query = searchResultDTO.getQuery();
        this.answer = searchResultDTO.getAnswer();
        this.username = username;
        this.searchType = searchType;
    }
}
