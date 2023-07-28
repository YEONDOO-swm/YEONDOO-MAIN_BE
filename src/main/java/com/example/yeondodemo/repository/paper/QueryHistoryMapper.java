package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.dto.QueryHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryHistoryMapper {
    void save(QueryHistory queryHistory);
    List<PaperHistory> findByUsernameAndPaperid(String username, String paperid);
    Integer getLastIdx(String username, String paperid);
}
