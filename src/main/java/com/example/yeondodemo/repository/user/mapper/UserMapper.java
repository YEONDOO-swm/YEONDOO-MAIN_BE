package com.example.yeondodemo.repository.user.mapper;

import com.example.yeondodemo.entity.Workspace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {
    public void save(@Param("saveParam")Workspace workspace);
    public Workspace findByName(Long workspaceId);
    public List<Workspace> findById(List<Long> workspaceIds);
    public void clearStore();
    public void update(@Param("updateParam") Workspace workspace);
}
