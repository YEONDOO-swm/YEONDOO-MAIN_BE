package com.example.yeondodemo.repository.etc.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordMapper {
    void save(Long workspaceId, List<String> keywords);
    List<String> findByUsername(Long workspaceId);
    void deleteByWorkspaceId(Long workspaceId);

}
