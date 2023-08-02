package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;

import java.util.List;

public interface QueryHistoryRepository {
    QueryHistory save(QueryHistory queryHistory);
    List<PaperHistory> findByUsernameAndPaperid(String username, String paperid);
    List<PaperHistoryDTO> findByUsername(String username);
    Integer getLastIdx(String username, String paperid);
    List<PaperHistory> findByUsernameAndPaperIdOrderQA(String username,String paperid);
}
