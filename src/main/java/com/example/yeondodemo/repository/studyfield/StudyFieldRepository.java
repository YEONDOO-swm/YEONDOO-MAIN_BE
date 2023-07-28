package com.example.yeondodemo.repository.studyfield;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyFieldRepository {
    public void save(String studyField);
    public void saveAll(List<String> studyFields);
    public String findById(Long id);
    public List<String> findAll();
    public void clear();
}
