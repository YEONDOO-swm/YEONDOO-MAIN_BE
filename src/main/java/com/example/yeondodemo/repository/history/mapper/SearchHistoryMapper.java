package com.example.yeondodemo.repository.history.mapper;

import com.example.yeondodemo.dto.history.SearchHistoryResponseDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.SearchHistory;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
@Mapper
public interface SearchHistoryMapper {
    List<SearchHistoryResponseDTO> findByUsername(Long workspaceId);

    Long findByRidAndUsername(Long workspaceId, Long rid);
    void savePapers(Long rid, List< PaperSimpleIdTitleDTO > papers);
    String findAnswerById(Long rid);
    String findQueryById(Long rid);
    void save(SearchHistory searchHistory);
    Long getLastId();
    List<PaperSimpleIdTitleDTO> findPapersById(Long id);
    Long canCached(Long workspaceId, String query, Integer searchType, LocalDate date);
    Long findByUsernameAndId(Long workspaceId, Long id);
    void updateScore(Long id, int score);

}
