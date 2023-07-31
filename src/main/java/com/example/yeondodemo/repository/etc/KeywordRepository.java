package com.example.yeondodemo.repository.etc;

import java.util.List;

public interface KeywordRepository {
    void save(String username, List<String> keywords);
    List<String> findByUsername(String username);
}
