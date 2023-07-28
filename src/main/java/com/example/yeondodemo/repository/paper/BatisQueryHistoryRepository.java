package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j @Repository @RequiredArgsConstructor
public class BatisQueryHistoryRepository implements QueryHistoryRepository{
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
    public Integer getLastIdx(String username, String paperid) {
        return queryHistoryMapper.getLastIdx(username, paperid);
    }
}
