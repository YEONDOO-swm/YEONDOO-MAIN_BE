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

import java.util.Date;
import java.util.List;

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
    public void checkPaperCanCached(String paperid){
        if((!paperBufferRepository.isHit(paperid))){
            //goto python server and get data
            log.info("go to python server.... ");
            Paper paper = paperRepository.findById(paperid);
            Thread apiRequestThread = new Thread(
                    () -> {

                        PaperPythonFirstResponseDTO pythonPaperInfoDTO = ConnectPythonServer.requestPaperInfo(paperid, pythonapi);
                        log.info("python return : {}", pythonPaperInfoDTO);
                        if(pythonPaperInfoDTO == null) {
                            throw new PythonServerException("Get Null Data From python Server");
                        }
                        updateInfoRepositoryV4(pythonPaperInfoDTO, paperid);
                    }
            );


            // 두 번째 작업: PDF 레퍼런스 추출
            Thread pdfExtractionThread = new Thread(
                    () -> {
                        List<String> references = PDFReferenceExtractor.ExtractReference(paper.getUrl());
                        paperRepository.saveReferences(references, paperid);
                    }
            );

            apiRequestThread.start();
            pdfExtractionThread.start();
            try {
                // 두 스레드가 모두 완료될 때까지 대기
                apiRequestThread.join();
                pdfExtractionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new PythonServerException("Python server Error");
            }

        };
    }
}
