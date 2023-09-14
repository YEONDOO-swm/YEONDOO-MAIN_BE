package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;
import com.example.yeondodemo.repository.history.mapper.QueryHistoryMapper;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j @Repository @RequiredArgsConstructor
public class BatisQueryHistoryRepository implements QueryHistoryRepository {
    private final QueryHistoryMapper queryHistoryMapper;
    @Override
    public QueryHistory save(QueryHistory queryHistory){
        log.info("save PaperInfo: {}", queryHistory);
        queryHistoryMapper.save(queryHistory);
        return queryHistory;
    }
    @Override
    public List<PaperHistory> findByUsernameAndPaperid(Long workspaceId, String paperid){
        log.info("findby username and paaperid : user: {} paepr: {} ",workspaceId,paperid);
        return queryHistoryMapper.findByUsernameAndPaperid(workspaceId, paperid);
    }

    @Override
    public List<PaperHistoryDTO> findByUsername(Long workspaceId) {
        return queryHistoryMapper.findByUsername(workspaceId);
    }

    @Override
    public Integer getLastIdx(Long workspaceId, String paperid) {
        return queryHistoryMapper.getLastIdx(workspaceId, paperid);
    }

    @Override
    public List<PaperHistory> findByUsernameAndPaperIdOrderQA(Long workspaceId, String paperid) {
        return queryHistoryMapper.findByUsernameAndPaperIdOrderQA(workspaceId, paperid);
    }

    @Override
    public List<PaperHistory> findByUserAndIdOrderQA4Python(Long workspaceId, String paperIsd) {
        return queryHistoryMapper.findByUserAndIdOrderQA4Python(workspaceId, paperIsd);
    }

    @Override
    public PaperHistory findByUsernameAndId(Long workspaceId, Long id) {
        return queryHistoryMapper.findByUsernameAndId(workspaceId, id);
    }

    @Override
    public void updateScore(Long id, Integer score) {
        queryHistoryMapper.updateScore(id, score);
    }


}
