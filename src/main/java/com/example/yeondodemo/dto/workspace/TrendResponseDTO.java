package com.example.yeondodemo.dto.workspace;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TrendResponseDTO {
    private String title;
    private Integer year;
    private String url;
}
