package com.example.yeondodemo.repository.user.mapper;

import com.example.yeondodemo.entity.Workspace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public void save(@Param("saveParam")Workspace workspace);
    public Workspace findByName(Long workspaceId);
    public void clearStore();
    public void update(@Param("updateParam") Workspace workspace);
}
