package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.PaperDTO;
import com.example.yeondodemo.dto.SearchResultDTO;
import com.example.yeondodemo.dto.history.HistorySearchDTO;
import com.example.yeondodemo.dto.history.PaperHistoryDTO;
import com.example.yeondodemo.dto.history.SearchHistoryResponseDTO;
import com.example.yeondodemo.dto.history.TrashContainerDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class HistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final LikePaperRepository likePaperRepository;
    private final PaperRepository paperRepository;
    private final QueryHistoryRepository queryHistoryRepository;
    public HistorySearchDTO historySearch(String username){
        List<SearchHistoryResponseDTO> results = searchHistoryRepository.findByUsername(username);
        List<PaperSimpleIdTitleDTO> papers =  likePaperRepository.findSimpleByUser(username);
        return new HistorySearchDTO(results, papers);
    }

    public SearchResultDTO historySearchResult(String username, Long rid){
        List<PaperSimpleIdTitleDTO> papers = searchHistoryRepository.findPapersById(rid);
        String query = searchHistoryRepository.findQueryById(rid);
        String answer = searchHistoryRepository.findAnswerById(rid);
        List<PaperDTO> paperDTOS = new ArrayList<>();
        for (PaperSimpleIdTitleDTO paper : papers) {
            paperDTOS.add(new PaperDTO(paperRepository.findById(paper.getPaperId())));
        }
        return new SearchResultDTO(query,answer, paperDTOS);
    }
    public TrashContainerDTO historySearchTrash(String username){
        List<PaperSimpleIdTitleDTO> trashContainers =  likePaperRepository.findTrashSimpleByUser(username);
        List<PaperSimpleIdTitleDTO> papers =  likePaperRepository.findSimpleByUser(username);
        return new TrashContainerDTO(trashContainers, papers);
    }
    public void historySearchRestoreTrash(String username, List<String> papers){
        for (String paper : papers) {
            likePaperRepository.update(username, paper, true);
        }
    }
    public List<PaperHistoryDTO> historyPaper(String username){
        return queryHistoryRepository.findByUsername(username);
    }
}
