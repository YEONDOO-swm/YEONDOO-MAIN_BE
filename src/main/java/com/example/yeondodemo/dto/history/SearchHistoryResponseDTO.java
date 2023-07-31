package com.example.yeondodemo.dto.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SearchHistoryResponseDTO {
    private Long rid;
    private String query;
}
