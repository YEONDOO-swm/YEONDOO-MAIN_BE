package com.example.yeondodemo.repository.studyfield;

import com.example.yeondodemo.repository.studyfield.mapper.StudyFieldMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @RequiredArgsConstructor @Slf4j
public class BatisStudyFieldRepository implements StudyFieldRepository{
    private final StudyFieldMapper studyFieldMapper;
    @Override
    public void save(String studyField) {
        studyFieldMapper.save(studyField);
    }

    @Override
    public void saveAll(List<String> studyFields) {
        studyFieldMapper.saveAll(studyFields);
    }

    @Override
    public String findById(Long id) {
        return null;
    }


    @Override
    public List<String> findAll() {
        return studyFieldMapper.findAll();
    }

    @Override
    public void clear() {
        studyFieldMapper.clear();
    }
}
