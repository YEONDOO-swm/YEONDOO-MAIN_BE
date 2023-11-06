package com.example.yeondodemo.repository.history.mapper;

import com.example.yeondodemo.dto.paper.PaperHistory;
import com.example.yeondodemo.dto.history.QueryHistory;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;
import com.example.yeondodemo.dto.python.Token;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryHistoryMapper {
    void save(QueryHistory queryHistory);
    List<PaperHistory> findByUsernameAndPaperid(Long workspaceId, String paperIsd);
    Long getLastIdx(Long workspaceId, String paperId);
    List<PaperHistoryDTO> findByUsername(Long workspaceId);
    List<PaperHistory> findByUsernameAndPaperIdOrderQA(Long workspaceId, String paperIsd);
    List<PaperHistory> findByUserAndIdOrderQA4Python(Long workspaceId, String paperIsd);
    PaperHistory findByUsernameAndId(Long workspaceId, Long id);
    void updateScore(Long id, Integer score);
    void updateToken(Long id, Token token);

}
