package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.entity.User;
import com.example.yeondodemo.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisUserRepository implements UserRepository{
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        userMapper.save(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public void clearStore() {
        userMapper.clearStore();
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }
}
