package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;
import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.dto.paper.item.ItemPosition;
import com.example.yeondodemo.dto.python.Token;
import com.example.yeondodemo.repository.history.mapper.QueryHistoryMapper;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j @Repository @RequiredArgsConstructor
public class BatisQueryHistoryRepository implements QueryHistoryRepository {
    private final QueryHistoryMapper queryHistoryMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public QueryHistory save(QueryHistory queryHistory){
        log.info("save PaperInfo: {}", queryHistory);
        queryHistoryMapper.save(queryHistory);
        return queryHistory;
    }
    @Override
    public List<PaperHistory> findByUsernameAndPaperid(Long workspaceId, String paperid){
        log.info("findby username and paaperid : user: {} paepr: {} ",workspaceId,paperid);
        List<PaperHistory> histories = queryHistoryMapper.findByUsernameAndPaperid(workspaceId, paperid);
        setPosition(histories, paperid);
        return histories;
    }

    @Override
    public List<PaperHistoryDTO> findByUsername(Long workspaceId) {
        return queryHistoryMapper.findByUsername(workspaceId);
    }

    @Override
    public Long getLastIdx(Long workspaceId, String paperid) {
        return queryHistoryMapper.getLastIdx(workspaceId, paperid);
    }
    public void setPosition(PaperHistory history, String paperId){
        try {
            history.setPosition(objectMapper.readValue(history.getPositionString(), ItemPosition.class));
            history.setPaperIds(List.of(history.getExtraPaperId(), paperId));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void setPosition(List<PaperHistory> histories, String paperId){
        for (PaperHistory history : histories) {
            setPosition(history, paperId);
        }
    }
    @Override
    public List<PaperHistory> findByUsernameAndPaperIdOrderQA(Long workspaceId, String paperid) {
        List<PaperHistory> histories = queryHistoryMapper.findByUsernameAndPaperIdOrderQA(workspaceId, paperid);
        setPosition(histories, paperid);
        return histories;
    }

    @Override
    public List<PaperHistory> findByUserAndIdOrderQA4Python(Long workspaceId, String paperIsd) {
        List<PaperHistory> histories = queryHistoryMapper.findByUserAndIdOrderQA4Python(workspaceId, paperIsd);
        setPosition(histories, paperIsd);
        return histories;
    }

    @Override
    public PaperHistory findByUsernameAndId(Long workspaceId, Long id) {
        PaperHistory history = queryHistoryMapper.findByUsernameAndId(workspaceId, id);
        return history;
    }

    @Override
    public void updateScore(Long id, Integer score) {
        queryHistoryMapper.updateScore(id, score);
    }

    @Override
    public void updateToken(Long id, Token token) {
        queryHistoryMapper.updateToken(id, token);
    }


}