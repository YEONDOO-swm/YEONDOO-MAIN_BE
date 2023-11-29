package com.example.yeondodemo.dto.token;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class TokenUsageDTO {
    private String email;
    private LocalDateTime usedDate;

    @Builder
    public TokenUsageDTO(String email, LocalDateTime usedDate) {
        this.email = email;
        this.usedDate = usedDate;
    }
}
