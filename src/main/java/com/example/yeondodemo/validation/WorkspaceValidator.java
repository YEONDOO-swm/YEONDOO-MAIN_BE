package com.example.yeondodemo.validation;

import com.example.yeondodemo.entity.LoginEntity;
import com.example.yeondodemo.repository.etc.LoginRedisRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Component;

@Slf4j @Component @RequiredArgsConstructor
public class WorkspaceValidator {
    private final LoginRedisRepository loginRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public Login login;
    @PostConstruct
    public void init(){
        this.login = new Login();
    }
    public class Login{
        public String getEmail(String jwt){
            return jwtTokenProvider.getUserName(jwt);
        }
        public Set<Long> get(String jwt){
            jwt = getEmail(jwt);
            return loginRedisRepository.findById(jwt).get().getLoginInfo();
        }
        public void put(String jwt, Set<Long> loginInfo){
            jwt = getEmail(jwt);
            log.info("Login.. {}", jwt);
            loginRedisRepository.save(new LoginEntity(jwt, loginInfo));
        }
        public void remove(String jwt, Long workspaceId){
            jwt = getEmail(jwt);
            Set<Long> workspaceIds = get(jwt);
            workspaceIds.remove(workspaceId);
            loginRedisRepository.save(new LoginEntity(jwt, workspaceIds));
        }
        public void delete(String jwt){
            jwt = getEmail(jwt);
            loginRedisRepository.deleteById(jwt);
        }
        public void add(String jwt, Long key){
            jwt = getEmail(jwt);
            Set<Long> workspaceIds = get(jwt);
            workspaceIds.add(key);
            loginRedisRepository.save(new LoginEntity(jwt, workspaceIds));
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
        login.delete(jwt);
    }

}
