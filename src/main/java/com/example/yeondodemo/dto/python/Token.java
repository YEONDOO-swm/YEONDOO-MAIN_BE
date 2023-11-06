package com.example.yeondodemo.dto.python;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Token {
    private Float promptTokens;
    private Float completionTokens;
    private Float totalCost;
}
