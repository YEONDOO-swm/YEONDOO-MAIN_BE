package com.example.yeondodemo.utils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pair <T, K>{
    private T first;
    private K second;
    public Pair(T f, K s){
        this.first = f;
        this.second = s;
    }
}
