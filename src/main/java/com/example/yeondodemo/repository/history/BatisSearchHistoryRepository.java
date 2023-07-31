package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.history.SearchHistoryResponseDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.SearchHistory;
import com.example.yeondodemo.repository.history.mapper.SearchHistoryMapper;
import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository @RequiredArgsConstructor
public class BatisSearchHistoryRepository implements SearchHistoryRepository{
    private final SearchHistoryMapper searchHistoryMapper;
    @Value("${python.address}") private String pythonapi;

    @Override
    public Long findByRidAndUsername(String username, Long rid) {
        return searchHistoryMapper.findByRidAndUsername(username, rid);
    }

    @Override
    public String findAnswerById(Long rid){
        return searchHistoryMapper.findAnswerById(rid);
    }

    @Override
    public String findQueryById(Long rid) {
        return searchHistoryMapper.findQueryById(rid);
    }

    @Override
    public List<SearchHistoryResponseDTO> findByUsername(String username) {
        return searchHistoryMapper.findByUsername(username);
    }

    @Override
    public void save(SearchHistory searchHistory) {
        searchHistoryMapper.save(searchHistory);
    }

    @Override
    public Long getLastId() {
        return searchHistoryMapper.getLastId();
    }

    @Override
    public void savePapers(List<PaperSimpleIdTitleDTO> papers) {
        Long rid = searchHistoryMapper.getLastId();
        searchHistoryMapper.savePapers(rid, papers);
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findPapersById(Long id) {
        return searchHistoryMapper.findPapersById(id);
    }
}
