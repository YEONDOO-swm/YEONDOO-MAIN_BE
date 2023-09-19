package com.example.yeondodemo.repository.user.batis;

import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.repository.user.mapper.RealUserMapper;
import com.example.yeondodemo.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisRealUserRepository implements RealUserRepository {
    private final RealUserMapper userMapper;
    private final UserMapper workspaceMapper;

    @Override
    public void save(String email) {
        userMapper.save(email);
    }
    @Override
    public Set<Long> findByName(String email) {
        return userMapper.findByName(email);
    }


    @Override
    public List<Workspace> findAll() {
        return null;
    }
    @Override
    public String exist(String email){
        return userMapper.exist(email);
    }
    @Override
    public void saveWorkspace(String email, Workspace workspace){
        workspaceMapper.save(workspace);
        userMapper.saveWorkspaceConnection(email, workspace.getWorkspaceId());

    }
    @Override
    public void updateWorkspaceValidity(Long workspaceId){
        userMapper.updateWorkspaceValidity(workspaceId);
    }
}
