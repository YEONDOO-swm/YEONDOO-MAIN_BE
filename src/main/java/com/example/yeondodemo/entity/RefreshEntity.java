package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter @RedisHash(value ="refresh", timeToLive = 172800000) @ToString
public class RefreshEntity {
    @Id
    private String id;
    private String refreshToken;
    private Long expired;
    public RefreshEntity(String id, String refreshToken){
        this.id = id;
        this.refreshToken = refreshToken;
        this.expired = System.currentTimeMillis() + 3600000 - 1000;
    }
}
