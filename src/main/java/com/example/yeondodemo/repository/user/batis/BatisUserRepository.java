package com.example.yeondodemo.repository.user.batis;

import com.example.yeondodemo.dto.workspace.WorkspacePutDTO;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.mapper.KeywordMapper;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisUserRepository implements UserRepository {
    private final UserMapper userMapper;
    private final KeywordMapper keywordMapper;
    @Override
    public Workspace save(Workspace workspace) {
        userMapper.save(workspace);
        return workspace;
    }

    @Override
    public List<Workspace> findById(List<Long> workspaceIds) {
        return userMapper.findById(workspaceIds);
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
    public WorkspacePutDTO update(WorkspacePutDTO user) {
        userMapper.update(user);
        keywordMapper.deleteByWorkspaceId(user.getWorkspaceId());
        keywordMapper.save(user.getWorkspaceId(), user.getKeywords());
        return user;
    }
}
