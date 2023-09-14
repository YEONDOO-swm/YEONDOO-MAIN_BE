package com.example.yeondodemo.service.login;

import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.KeywordRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j @Service @RequiredArgsConstructor
public class LoginService {
    private final StudyFieldRepository studyFieldRepository;
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;

}
