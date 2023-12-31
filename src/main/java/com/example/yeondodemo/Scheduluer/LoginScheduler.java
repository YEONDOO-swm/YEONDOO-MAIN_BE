package com.example.yeondodemo.Scheduluer;

import com.example.yeondodemo.service.search.PaperService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j @RequiredArgsConstructor
public class LoginScheduler {
    private final JwtTokenProvider provider;
    private final PaperService paperService;
    private final WorkspaceValidator workspaceValidator;
}
