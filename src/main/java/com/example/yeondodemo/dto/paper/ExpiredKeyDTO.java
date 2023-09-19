package com.example.yeondodemo.dto.paper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class ExpiredKeyDTO {
    private Long expired;
    private Long rid;

    public ExpiredKeyDTO(Long rid) {
        this.expired = System.currentTimeMillis() + 10 * 60 * 1000; // 현재 시간에 10분을 더한 값
        this.rid = rid;
    }
}
