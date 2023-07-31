package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.paper.Paper4Container;
import com.example.yeondodemo.dto.paper.PaperContainerDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.SearchHistory;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import com.example.yeondodemo.repository.paper.*;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service @Slf4j @RequiredArgsConstructor
public class SearchService {
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private final UserRepository userRepository;
    private final BatisAuthorRepository authorRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    @Transactional
    public SearchResultDTO search(SearchResultDTO searchResultDTO, PythonResultDTO pythonResultDTO, String username){
        searchResultDTO.setAnswer(pythonResultDTO.getAnswer());
        //List<String> papers = pythonResultDTO.getPapers();
        List<TestPython> papers = pythonResultDTO.getPapers();
        log.info("python paper: {}", papers.toString());
        User user = userRepository.findByName(username);
        List<String> userSet = likePaperRepository.findByUser(user.getUsername());
        List<PaperSimpleIdTitleDTO> paperList = new ArrayList<>();
        for (TestPython tPaper : papers) {
            Paper paper = paperRepository.findById(tPaper.getPaperId());
            PaperDTO paperDTO = new PaperDTO(tPaper, paper);
            if (userSet != null && userSet.contains(paper.getPaperId())){ //userSEt null인부분 생각
                paperDTO.setIsLike(true);
            }
            paperList.add(new PaperSimpleIdTitleDTO(paper));
            searchResultDTO.getPapers().add(paperDTO);
        }
        searchHistoryRepository.save(new SearchHistory(searchResultDTO, username));
        searchHistoryRepository.savePapers(paperList);
//        for (String id : papers) {
//            log.info(id);
//            Paper paper = paperRepository.findById(id®);
//            PaperDTO paperDTO = new PaperDTO(paper);
//            if (userSet != null && userSet.contains(paper)){ //userSEt null인부분 생각
//                paperDTO.setIslike(true);
//            }
//            searchResultDTO.getPapers().add(paperDTO);
//        }
        return searchResultDTO;
    }
    @Transactional
    public void likeonoff(LikeOnOffDTO likeOnOffDTO){
        String username = likeOnOffDTO.getUsername();
        String paperId = likeOnOffDTO.getPaperId();
        boolean onoff = likeOnOffDTO.isOn();
        List<String> userSet = likePaperRepository.findAllByUser(username);
        //Paper paper = paperRepository.findById(paperId);
        if(userSet.contains(paperId)){
            likePaperRepository.update(username, paperId, onoff);
        }else{
            likePaperRepository.save(username, paperId, onoff);
        }
        if(onoff){
            paperRepository.add(paperId);
        }else{
            paperRepository.sub(paperId);
        }
    }
    public List<Paper4Container> getPaperContainer(String username){
        List<String> paperSet = likePaperRepository.findByUser(username);
        List<Paper4Container> ret = new ArrayList<>();
        for(String paperId: paperSet){
            Paper paper = paperRepository.findById(paperId);
            ret.add(new Paper4Container(paper));
        }
        return ret;
    }

}
