package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.paper.PaperHistory;
import com.example.yeondodemo.dto.history.QueryHistory;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;
import com.example.yeondodemo.dto.python.Token;

import java.util.List;

public interface QueryHistoryRepository {
    QueryHistory save(QueryHistory queryHistory);
    List<PaperHistory> findByUsernameAndPaperid(Long workspaceId, String paperid);
    List<PaperHistoryDTO> findByUsername(Long workspaceId);
    Long getLastIdx(Long workspaceId, String paperid);
    List<PaperHistory> findByUsernameAndPaperIdOrderQA(Long workspaceId,String paperid);
    List<PaperHistory> findByUserAndIdOrderQA4Python(Long workspaceId, String paperIsd);
    PaperHistory findByUsernameAndId(Long workspaceId, Long id);
    void updateScore(Long id, Integer score);
    void updateToken(Long id, Token token);
}
