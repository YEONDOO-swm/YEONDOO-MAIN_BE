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
    public SearchHistory(SearchResultDTO searchResultDTO, String username){
        this.query = searchResultDTO.getQuery();
        this.answer = searchResultDTO.getAnswer();
        this.username =username;
    }
}
