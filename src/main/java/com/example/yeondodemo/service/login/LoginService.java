package com.example.yeondodemo.service.login;

import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.LoginUserDTO;
import com.example.yeondodemo.dto.UserProfileDTO;
import com.example.yeondodemo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j @Service @RequiredArgsConstructor
public class LoginService {
    private final StudyFieldRepository studyFieldRepository;
    private final UserRepository userRepository;
    public static Map<User, Date> loginUsers = new HashMap<>();//@1Login기능
    public User join(LoginUserDTO loginUserDTO){
        User user = userRepository.findByName(loginUserDTO.getUsername());
        loginUsers.put(user, new Date()); //@1Login기능
        return user;
    }
    public void setUserProfile(UserProfileDTO userProfileDTO){
        User user = userRepository.findByName(userProfileDTO.getUsername());
        user.setKeywords(userProfileDTO.getKeywords());
        user.setStudyField(userProfileDTO.getStudyField());
        user.setIsFirst(false);
        //loginUsers.put(user, new Date());//@1Login기능\
        userRepository.update(user);

    }
    public boolean isValidUser(String username){
        User user = userRepository.findByName(username);
        if(user==null){return false;}
        return true;
    }
    public boolean checkNotFirst(String username){
        User user = userRepository.findByName(username);
        return !user.getIsFirst();
    }

}
