package com.example.yeondodemo.repository.user.batis;

import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisUserRepository implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public Workspace save(Workspace workspace) {
        userMapper.save(workspace);
        return workspace;
    }

    @Override
    public Workspace findById(Long id) {
        return null;
    }

    @Override
    public List<Workspace> findAll() {
        return null;
    }

    @Override
    public Workspace findByName(Long name) {
        return userMapper.findByName(name);
    }

    @Override
    public void clearStore() {
        userMapper.clearStore();
    }

    @Override
    public Workspace update(Workspace user) {
        userMapper.update(user);
        return user;
    }
}
