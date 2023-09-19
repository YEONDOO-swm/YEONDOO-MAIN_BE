package com.example.yeondodemo.filter;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* com.example.yeondodemo.Controller..*(..)) && !@annotation(com.example.yeondodemo.filter.WorkspaceAdd)")
    public void allController(){}


    @Pointcut("@annotation(com.example.yeondodemo.filter.WorkspaceAdd)")
    public void targetWorkspaceAdd(){}

}
