package com.example.yeondodemo.validation;

import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@Slf4j
public class LoginValidator {
    private static UserRepository userRepository;
    public static void init(ApplicationContext context){
        userRepository = context.getBean(UserRepository.class);
    }
    public static boolean isValid(String username, String password){
        Optional<User> user = Optional.ofNullable(userRepository.findByName(username));
        String pw = user.map(User::getPassword).orElse("inVal");
        return pw.equals(password);
    }
    public static boolean isNotValidName(String username){
        return userRepository.findByName(username)==null;
    }
}
