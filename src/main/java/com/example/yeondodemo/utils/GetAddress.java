package com.example.yeondodemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class GetAddress {
    @Value("${python.address}")
    private final String pythonApiServer; //파이썬 서버 주소 알아내고 수정하기

    public String getADD(){
        return pythonApiServer;
    }
}
