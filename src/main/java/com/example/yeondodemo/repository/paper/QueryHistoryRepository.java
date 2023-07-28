package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;

import java.util.List;

public interface QueryHistoryRepository {
    QueryHistory save(QueryHistory queryHistory);
    List<PaperHistory> findByUsernameAndPaperid(String username, String paperid);
    Integer getLastIdx(String username, String paperid);
}
