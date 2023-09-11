package com.example.yeondodemo.validation;

import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserValidator {
    private static UserRepository userRepository;
    public static Map<String, User> login;
    public static void init(ApplicationContext context){

        userRepository = context.getBean(UserRepository.class);
        login =  new ConcurrentHashMap<>();
    }
    public static boolean isValid(String username, String password){
        Optional<User> user = Optional.ofNullable(userRepository.findByName(username));
        String pw = user.map(User::getPassword).orElse("inVal");
        return pw.equals(password);
    }
    public static boolean isFirst(String username){
        return login.get(username).getIsFirst();
    }

    public static boolean isNotValidName(String username){
        return login.get(username) == null;
    }
}
