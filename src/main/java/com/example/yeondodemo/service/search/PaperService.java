package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.QueryHistoryRepository;
import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor @Slf4j
public class PaperService {
    private final QueryHistoryRepository queryHistoryRepository;
    private final PaperInfoRepository paperInfoRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperRepository paperRepository;
    private final BatisAuthorRepository authorRepository;
    @Value("${python.address}")
    private String pythonapi;
    @Transactional
    public Map getPaperQuestion(String paperid, String username, String query){
        List<PaperHistory> histories = queryHistoryRepository.findByUsernameAndPaperid(username, paperid);
        String answer = ConnectPythonServer.question(new PythonQuestionDTO(paperid, histories, query), pythonapi);
        Integer idx = queryHistoryRepository.getLastIdx(username, paperid);
        if(idx == null) {idx=0;}
        queryHistoryRepository.save(new QueryHistory(username, paperid, idx+1, true, query));
        queryHistoryRepository.save(new QueryHistory(username, paperid, idx+2, false, answer));
        Map<String, String> ret = new HashMap<>();
        ret.put("answer", answer);
        return ret;
    }
    public void updateInfoRepository(PythonPaperInfoDTO pythonPaperInfoDTO, String paperid){
//        for(String insight: pythonPaperInfoDTO.getInsights()){
//            paperInfoRepository.save(new PaperInfo(paperid, "insight", insight));
//        }
        for(String q: pythonPaperInfoDTO.getQuestions()){
            paperInfoRepository.save(new PaperInfo(paperid, "question", q));
        }
        for(String s: pythonPaperInfoDTO.getSubjectrecommends()){
            paperInfoRepository.save(new PaperInfo(paperid, "subjectrecommend", s));
        }
//        for(String r: pythonPaperInfoDTO.getReferences()){
//            paperInfoRepository.save(new PaperInfo(paperid, "reference", r));
//        }
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
        paperRepository.updateSummary(paperid, pythonPaperInfoDTO.getSummary());
    }
    @Transactional
    public RetPaperInfoDTO getPaperInfo(String paperid, String username){
        //todo: 시간지나면 다시 업데이트 추가.
        if (paperBufferRepository.isHit(paperid) == null){
            paperBufferRepository.save(new PaperBuffer(paperid, false));
        }
        log.info("getPaperInfo... ");
        if(!paperBufferRepository.isHit(paperid)){
            //goto python server and get data
            log.info("go to python server.... ");
            PythonPaperInfoDTO pythonPaperInfoDTO = ConnectPythonServer.requestPaperInfo(paperid, pythonapi);

            log.info("python return : {}", pythonPaperInfoDTO);
            if(pythonPaperInfoDTO == null) return null;
            updateInfoRepository(pythonPaperInfoDTO, paperid);
        };
        PythonPaperInfoDTO pythonPaperInfoDTO = new PythonPaperInfoDTO();
        //pythonPaperInfoDTO.setInsights(paperInfoRepository.findByPaperIdAndType(paperid, "insight"));
        // pythonPaperInfoDTO.setReferences(paperInfoRepository.findByPaperIdAndType(paperid, "reference"));
        pythonPaperInfoDTO.setQuestions(paperInfoRepository.findByPaperIdAndType(paperid, "question"));
        pythonPaperInfoDTO.setSubjectrecommends(paperInfoRepository.findByPaperIdAndType(paperid, "subjectrecommend"));
        pythonPaperInfoDTO.setSummary(paperRepository.findSummaryById(paperid));
        Paper paper = paperRepository.findFullById(paperid);
        paper.setAuthors(authorRepository.findByPaperId(paperid));
        RetPaperInfoDTO paperInfoDTO = new RetPaperInfoDTO(paper, pythonPaperInfoDTO, queryHistoryRepository.findByUsernameAndPaperid(username, paperid));
        log.info("paper info: {}", paperInfoDTO);
        return paperInfoDTO;
    }

}
