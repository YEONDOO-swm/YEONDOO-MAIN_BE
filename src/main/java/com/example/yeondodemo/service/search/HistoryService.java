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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor @Slf4j
public class HistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final LikePaperRepository likePaperRepository;
    private final PaperRepository paperRepository;
    private final QueryHistoryRepository queryHistoryRepository;
    public Map<String, List<SearchHistoryResponseDTO>> historySearch(Long workspaceId){
        List<SearchHistoryResponseDTO> results = searchHistoryRepository.findByUsername(workspaceId);
        Map<String, List<SearchHistoryResponseDTO>> ret = new HashMap<>();
        ret.put("results", results);
        return ret;
    }
    public void historySearchRestoreTrash(Long workspaceId, List<String> papers){
        for (String paper : papers) {
            likePaperRepository.update(workspaceId, paper, true);
            paperRepository.add(paper);
        }
    }

    public SearchResultDTO historySearchResult(Long workspaceId, Long rid){
        List<PaperSimpleIdTitleDTO> papers = searchHistoryRepository.findPapersById(rid);
        String query = searchHistoryRepository.findQueryById(rid);
        String answer = searchHistoryRepository.findAnswerById(rid);
        List<PaperDTO> paperDTOS = new ArrayList<>();
        for (PaperSimpleIdTitleDTO paper : papers) {
            paperDTOS.add(new PaperDTO(paperRepository.findById(paper.getPaperId())));
        }
        return new SearchResultDTO(query,answer,rid, paperDTOS);
    }
    public TrashContainerDTO historySearchTrash(Long workspaceId){
        List<PaperSimpleIdTitleDTO> trashContainers =  likePaperRepository.findTrashSimpleByUser(workspaceId);
        List<PaperSimpleIdTitleDTO> papers =  likePaperRepository.findSimpleByUser(workspaceId);
        return new TrashContainerDTO(trashContainers, papers);
    }

    public List<PaperHistoryDTO> historyPaper(Long workspaceId){
        return queryHistoryRepository.findByUsername(workspaceId);
    }
}
