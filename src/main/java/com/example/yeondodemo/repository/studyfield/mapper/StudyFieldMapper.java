package com.example.yeondodemo.repository.studyfield.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyFieldMapper {
    public void save(String studyField);
    public void saveAll(List<String> studyFields);
    public List<String> findAll();
    public void clear();
}
