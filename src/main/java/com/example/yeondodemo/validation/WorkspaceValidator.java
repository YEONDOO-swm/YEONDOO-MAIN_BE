package com.example.yeondodemo.validation;

import com.example.yeondodemo.entity.LoginEntity;
import com.example.yeondodemo.repository.etc.LoginRedisRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j @Component @RequiredArgsConstructor
public class WorkspaceValidator {
    private final LoginRedisRepository loginRedisRepository;
    public Login login;
    @PostConstruct
    public void init(){
        this.login = new Login();
    }
    public class Login{
        public Set<Long> get(String jwt){
            return loginRedisRepository.findById(jwt).get().getLoginInfo();
        }
        public void put(String jwt, Set<Long> loginInfo){
            log.info("Login.. {}", jwt);
            loginRedisRepository.save(new LoginEntity(jwt, loginInfo));
        }
        public void remove(String jwt){
            loginRedisRepository.deleteById(jwt);
        }
    }
    public void addLogin(String jwt, Set<Long> workspaceList) {
        login.put(jwt, workspaceList);
    }

    public boolean isValid(String jwt, Long workspaceId) {
        try{
            return login.get(jwt).contains(workspaceId);
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public void logout(String jwt) {
        login.remove(jwt);
    }

}
