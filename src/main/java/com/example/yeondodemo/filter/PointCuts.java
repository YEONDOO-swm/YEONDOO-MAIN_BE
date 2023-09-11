package com.example.yeondodemo.filter;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* com.example.yeondodemo.Controller..*(..))")
    public void allController(){}
}
