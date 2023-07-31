package com.example.yeondodemo.repository.history;

import com.example.yeondodemo.dto.history.SearchHistoryResponseDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.SearchHistory;

import java.util.List;

public interface SearchHistoryRepository {
     Long findByRidAndUsername(String username, Long rid);
     String findAnswerById(Long rid);
     String findQueryById(Long rid);

     List<SearchHistoryResponseDTO> findByUsername(String username);
     void save(SearchHistory searchHistory);
     Long getLastId();
     void savePapers(List<PaperSimpleIdTitleDTO> papers);
     List<PaperSimpleIdTitleDTO> findPapersById(Long id);


}
