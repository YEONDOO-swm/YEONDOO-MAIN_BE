package com.example.yeondodemo.repository.etc.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordMapper {
    void save(String username, List<String> keywords);
    List<String> findByUsername(String username);

}
