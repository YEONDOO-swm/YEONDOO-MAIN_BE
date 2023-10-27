package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.BufferUpdateDTO;
import com.example.yeondodemo.dto.PaperInfo;
import com.example.yeondodemo.dto.python.PaperPythonFirstResponseDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.exceptions.PythonServerException;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.utils.ConnectPythonServer;
import com.example.yeondodemo.utils.PDFReferenceExtractor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class Cache {
    private final PaperInfoRepository paperInfoRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperRepository paperRepository;
    @Value("${python.address}")
    private String pythonapi;
    public void updateInfoRepositoryV4(PaperPythonFirstResponseDTO pythonPaperInfoDTO, String paperid){
        String summary = pythonPaperInfoDTO.getSummary();
        List<String> questions = pythonPaperInfoDTO.getQuestions();
        paperInfoRepository.save(new PaperInfo(paperid, "summary", summary));
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
    }

    @Transactional
    public PaperPythonFirstResponseDTO checkPaperCanCached(String paperid, String fileName){
        if((!paperBufferRepository.isHit(paperid))){
            //goto python server and get data
            log.info("go to python server.... ");

            Paper paper = paperRepository.findById(paperid);
            PaperPythonFirstResponseDTO pythonPaperInfoDTO = ConnectPythonServer.requestPaperInfo(paperid, pythonapi, paper.getUserPdf(), fileName);
            log.info("python return : {}", pythonPaperInfoDTO);
                            if(pythonPaperInfoDTO == null) {
                                throw new RuntimeException("Get Null Data From python Server");
                            }
            return pythonPaperInfoDTO;
        }
        return null;
    }
}
