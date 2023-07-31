package com.example.yeondodemo.dto.history;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HistorySearchDTO {
    List<PaperSimpleIdTitleDTO> papers;
    List<SearchHistoryResponseDTO> results;
    public HistorySearchDTO(List<SearchHistoryResponseDTO> results, List<PaperSimpleIdTitleDTO> papers){
        this.results = results;
        this.papers = papers;
    }
}
