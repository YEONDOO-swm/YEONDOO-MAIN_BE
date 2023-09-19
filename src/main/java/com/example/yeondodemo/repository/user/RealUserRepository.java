package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.entity.Workspace;

import java.util.List;
import java.util.Set;

public interface RealUserRepository {
    void save(String email);
    Set<Long> findByName(String email);
    List<Workspace> findAll();
    String exist(String email);
    void saveWorkspace(String email, Workspace workspace);
    void updateWorkspaceValidity(Long workspaceId);
}
