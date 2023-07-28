package com.example.yeondodemo.repository.studyfield;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MemoryStudyFieldRepository implements StudyFieldRepository{
    private static final Map<Long, String> store = new HashMap<>();
    private static Long sequence = 0L;
    @Override
    public void save(String studyField) {
        store.put(++sequence, studyField);
    }

    @Override
    public void saveAll(List<String> studyFields) {
    }

    @Override
    public String findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<String>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
