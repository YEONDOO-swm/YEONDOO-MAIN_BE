package com.example.yeondodemo.repository.etc;

import java.util.List;

public interface KeywordRepository {
    void save(Long workspaceId, List<String> keywords);
    List<String> findByUsername(Long workspaceId);
}
