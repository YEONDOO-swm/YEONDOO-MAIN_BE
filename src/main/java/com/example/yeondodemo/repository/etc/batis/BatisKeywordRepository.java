package com.example.yeondodemo.repository.etc.batis;

import com.example.yeondodemo.repository.etc.KeywordRepository;
import com.example.yeondodemo.repository.etc.mapper.KeywordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @RequiredArgsConstructor
public class BatisKeywordRepository implements KeywordRepository {
    private final KeywordMapper keywordMapper;
    @Override
    public void save(Long workspaceId, List<String> keywords) {
        keywordMapper.save(workspaceId, keywords);
    }

    @Override
    public List<String> findByUsername(Long workspaceId) {
        return keywordMapper.findByUsername(workspaceId);
    }
}
