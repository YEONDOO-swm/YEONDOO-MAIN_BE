package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.paper.*;
import com.example.yeondodemo.dto.python.PythonResultDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.SearchHistory;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import com.example.yeondodemo.repository.paper.*;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j @RequiredArgsConstructor
public class SearchService {
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private final UserRepository userRepository;
    private final BatisAuthorRepository authorRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    @Value("${python.address}")
    private String pythonapi;
    @Transactional
    public void resultScore(PaperResultRequest paperResultRequest){
        searchHistoryRepository.updateScore(paperResultRequest.getId(), paperResultRequest.getScore());
    }

    public PythonResultDTO checkSearchResultCanCached(String query, Long workspaceId, Integer searchType){
        PythonResultDTO pythonResultDTO = null;
        Long rid = searchHistoryRepository.canCached(workspaceId, query, searchType); //마이에스큐엘 캐쉬여부 찾기. 레디스로 캐쉬 옮기기 

        if(rid > 0){
            log.info("cached.. ");
            List<PaperSimpleIdTitleDTO> paperTitles = searchHistoryRepository.findPapersById(rid);
            String answer = searchHistoryRepository.findAnswerById(rid);
            List<TestPython> papers = new ArrayList<>();
            for (PaperSimpleIdTitleDTO paperTitle : paperTitles) {
                papers.add(new TestPython(paperRepository.findById(paperTitle.getPaperId())));
            }
            pythonResultDTO = new PythonResultDTO(answer, papers);
        }else{
            log.info("not cached.. ");
            pythonResultDTO = ConnectPythonServer.request(query, searchType, pythonapi); //들어오게 정리. 변수명 직관적이게
        }
        return pythonResultDTO;

    }

    @Transactional
    public SearchResultDTO search(String query, Long workspaceId, Integer searchType){
        StopWatch stopWatch = new StopWatch();
        SearchResultDTO searchResultDTO = new SearchResultDTO(query);
        PythonResultDTO pythonResultDTO = checkSearchResultCanCached(query, workspaceId, searchType);

        stopWatch.start("Setting");
        searchResultDTO.setAnswer(pythonResultDTO.getAnswer());
        //List<String> papers = pythonResultDTO.getPapers();
        List<TestPython> papers = pythonResultDTO.getPapers();
        log.info("python paper: {}", papers.toString());
        Workspace workspace = userRepository.findByName(workspaceId);
        List<String> userSet = likePaperRepository.findByUser(workspace.getWorkspaceId());
        List<PaperSimpleIdTitleDTO> paperList = new ArrayList<>();
        stopWatch.stop();


        stopWatch.start("for ..");
        for (TestPython tPaper : papers) {
            if (tPaper.getPaperId().matches("^[0-9]{7}$")) {
                String category = tPaper.getCategories().get(0).toString();
                if (category.contains(".")) {
                    String[] categoryParts = category.split("\\.");
                    category = categoryParts[0]; // 앞의 부분만 사용
                }
                log.info("change paperId to {}", category + "/" + tPaper.getPaperId());
                tPaper.setPaperId(category + "/" + tPaper.getPaperId());
            }
            Paper paper = paperRepository.findById(tPaper.getPaperId());
            if(paper == null){
                log.info("new Paper.. save..");
                paper = new Paper(tPaper);
                paperRepository.save(paper);

                //set Author
                for (String author : paper.getAuthors()) {
                    authorRepository.save(paper.getPaperId(), author);
                }
            }
            PaperDTO paperDTO = new PaperDTO(paper);
            if (userSet != null && userSet.contains(paper.getPaperId())){ //userSEt null인부분 생각
                paperDTO.setIsLike(true);
            }
            paperList.add(new PaperSimpleIdTitleDTO(paper));
            searchResultDTO.getPapers().add(paperDTO);
        }
        stopWatch.stop();
        if(paperList == null || paperList.size() == 0){
            searchResultDTO.setAnswer("아니아니아니");
        }else{
            searchResultDTO.setAnswer("");
        }

        //save
        searchHistoryRepository.save(new SearchHistory(searchResultDTO, workspaceId, searchType));
        searchResultDTO.setId(searchHistoryRepository.getLastId());
        searchHistoryRepository.savePapers(paperList);

        log.info(stopWatch.shortSummary());
        log.info(String.valueOf(stopWatch.getTotalTimeMillis()));
        log.info(stopWatch.prettyPrint());
        return searchResultDTO;
    }

    @Transactional
    public void likeonoff(LikeOnOffDTO likeOnOffDTO){
        Long workspaceId = likeOnOffDTO.getWorkspaceId();
        String paperId = likeOnOffDTO.getPaperId();
        boolean onoff = likeOnOffDTO.isOn();
        List<String> userSet = likePaperRepository.findAllByUser(workspaceId);
        //Paper paper = paperRepository.findById(paperId);
        if(userSet.contains(paperId)){
            likePaperRepository.update(workspaceId, paperId, onoff);
        }else{
            likePaperRepository.save(workspaceId, paperId, onoff);
        }
        if(onoff){
            paperRepository.add(paperId);
        }else{
            paperRepository.sub(paperId);
        }
    }
    public List<Paper4Container> getPaperContainer(Long workspaceId){
        List<String> paperSet = likePaperRepository.findByUser(workspaceId);
        List<Paper4Container> ret = new ArrayList<>();
        for(String paperId: paperSet){
            Paper paper = paperRepository.findById(paperId);
            ret.add(new Paper4Container(paper));
        }
        return ret;
    }

}
