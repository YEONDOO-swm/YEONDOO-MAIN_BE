package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Getter
@RedisHash(value ="login", timeToLive = 3600000) @ToString
public class LoginEntity {
    @Id
    private String jwt;
    private Set<Long> loginInfo;
    public LoginEntity(String jwt, Set<Long> loginInfo){
        this.jwt = jwt;
        this.loginInfo = loginInfo;
    }
}
