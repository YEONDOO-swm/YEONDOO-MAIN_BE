package com.example.yeondodemo.repository.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface RealUserMapper {
    public void save(String email);
    public Set<Long> findByName(String email);
    public String exist(String email);
    public void saveWorkspaceConnection(String email, Long workspaceId);
    public void updateWorkspaceValidity(Long workspaceId);
}
