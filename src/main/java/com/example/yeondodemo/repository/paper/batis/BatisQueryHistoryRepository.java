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
    public List<PaperHistory> findByUsernameAndPaperid(String username, String paperid){
        log.info("findby username and paaperid : user: {} paepr: {} ",username,paperid);
        return queryHistoryMapper.findByUsernameAndPaperid(username, paperid);
    }

    @Override
    public List<PaperHistoryDTO> findByUsername(String username) {
        return queryHistoryMapper.findByUsername(username);
    }

    @Override
    public Integer getLastIdx(String username, String paperid) {
        return queryHistoryMapper.getLastIdx(username, paperid);
    }

    @Override
    public List<PaperHistory> findByUsernameAndPaperIdOrderQA(String username, String paperid) {
        return queryHistoryMapper.findByUsernameAndPaperIdOrderQA(username, paperid);
    }

    @Override
    public List<PaperHistory> findByUserAndIdOrderQA4Python(String username, String paperIsd) {
        return queryHistoryMapper.findByUserAndIdOrderQA4Python(username, paperIsd);
    }

    @Override
    public PaperHistory findByUsernameAndId(String username, Long id) {
        return queryHistoryMapper.findByUsernameAndId(username, id);
    }

    @Override
    public void updateScore(Long id, Integer score) {
        queryHistoryMapper.updateScore(id, score);
    }


}
