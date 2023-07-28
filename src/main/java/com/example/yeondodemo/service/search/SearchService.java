package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.paper.Paper4Container;
import com.example.yeondodemo.dto.paper.PaperContainerDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.paper.*;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service @Slf4j @RequiredArgsConstructor
public class SearchService {
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private final UserRepository userRepository;
    private final BatisAuthorRepository authorRepository;

    public SearchResultDTO search(SearchResultDTO searchResultDTO, PythonResultDTO pythonResultDTO, String username){
        searchResultDTO.setAnswer(pythonResultDTO.getAnswer());
        //List<String> papers = pythonResultDTO.getPapers();
        List<TestPython> papers = pythonResultDTO.getPapers();
        log.info("python paper: {}", papers.toString());
        User user = userRepository.findByName(username);
        List<String> userSet = likePaperRepository.findByUser(user.getUsername());
        for (TestPython tPaper : papers) {
            Paper paper = paperRepository.findFullById(tPaper.getPaperId());
            PaperDTO paperDTO = new PaperDTO(tPaper, paper);
            if (userSet != null && userSet.contains(paper.getPaperId())){ //userSEt null인부분 생각
                paperDTO.setIslike(true);
            }
            searchResultDTO.getPapers().add(paperDTO);
        }
//        for (String id : papers) {
//            log.info(id);
//            Paper paper = paperRepository.findById(id);
//            PaperDTO paperDTO = new PaperDTO(paper);
//            if (userSet != null && userSet.contains(paper)){ //userSEt null인부분 생각
//                paperDTO.setIslike(true);
//            }
//            searchResultDTO.getPapers().add(paperDTO);
//        }
        return searchResultDTO;
    }

    public PaperDTO likeonoff(LikeOnOffDTO likeOnOffDTO){
        String username = likeOnOffDTO.getUsername();
        String paperId = likeOnOffDTO.getPaperId();
        boolean onoff = likeOnOffDTO.isOnoff();
        List<String> userSet = likePaperRepository.findAllByUser(username);
        Paper paper = paperRepository.findById(paperId);
        if(userSet.contains(paperId)){
            likePaperRepository.update(username, paperId, onoff);
        }else{
            likePaperRepository.save(username, paperId, onoff);
        }
        if(onoff){
            paper.addLike();
        }else{
            paper.subLike();
        }
        paperRepository.update(paperId, paper);
        return new PaperDTO(paper);
    }
    public List<Paper4Container> getPaperContainer(String username){
        List<String> paperSet = likePaperRepository.findByUser(username);
        List<Paper4Container> ret = new ArrayList<>();
        for(String paperId: paperSet){
            Paper paper = paperRepository.findById(paperId);
            List<String> authors = authorRepository.findByPaperId(paperId);
            ret.add(new Paper4Container(paper, authors));
        }
        return ret;
    }

}
