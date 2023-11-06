package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.history.SearchHistoryResponseDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.SearchHistory;
import com.example.yeondodemo.repository.history.mapper.SearchHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository @RequiredArgsConstructor
public class BatisSearchHistoryRepository implements SearchHistoryRepository{
    private final SearchHistoryMapper searchHistoryMapper;
    @Value("${python.address}") private String pythonapi;

    @Override
    public Long findByRidAndUsername(Long workspaceId, Long rid) {
        return searchHistoryMapper.findByRidAndUsername(workspaceId, rid);
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
    public List<SearchHistoryResponseDTO> findByUsername(Long workspaceId) {
        return searchHistoryMapper.findByUsername(workspaceId);
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
        if(papers != null && papers.size() > 0){
            searchHistoryMapper.savePapers(rid, papers);
        }
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findPapersById(Long id) {
        return searchHistoryMapper.findPapersById(id);
    }

    @Override
    public Long canCached(Long workspaceId, String query, Integer searchType) {
        return searchHistoryMapper.canCached(workspaceId, query, searchType, LocalDate.now());
    }

    @Override
    public Long findByUsernameAndId(Long workspaceId, Long id) {
        return searchHistoryMapper.findByUsernameAndId(workspaceId, id);
    }

    @Override
    public void updateScore(Long id, int score) {
        searchHistoryMapper.updateScore(id, score);
    }
}
