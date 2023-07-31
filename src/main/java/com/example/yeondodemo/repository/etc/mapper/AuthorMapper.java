package com.example.yeondodemo.repository.etc.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorMapper {
    void save(String paperId, String author);
    void saveAll(String paperId, List<String> authors);
    List<String> findByPaperId(String paperId);
}
