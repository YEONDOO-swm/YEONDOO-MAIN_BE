package com.example.yeondodemo.validation;

import com.example.yeondodemo.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WorkspaceValidator {
    private static UserRepository userRepository;
    public static Map<String, Set<Long>> login;
    public static void addLogin(String jwt, Set workspaceList){
        login.put(jwt, workspaceList);
    }
    public static void init(ApplicationContext context){

        userRepository = context.getBean(UserRepository.class);
        login =  new ConcurrentHashMap<>();
    }
    public static boolean isValid(String jwt, Long workspaceId){
        return login.get(jwt).contains(workspaceId);
    }
}
