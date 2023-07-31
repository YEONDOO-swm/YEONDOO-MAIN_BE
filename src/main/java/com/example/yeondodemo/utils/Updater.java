package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.ScholarDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component @RequiredArgsConstructor
public class Updater {
    private final PaperMapper paperMapper;
    @Value("${serpapi.key}") private String key;
    @Transactional
    public Paper save(Paper paper){
        paperMapper.save(new Paper(paper.getPaperId()));
        return update(paper);
    }
    @Transactional
    public Paper update(Paper paper){
        if(paper.getLastUpdate() == null || ChronoUnit.DAYS.between(paper.getLastUpdate() ,LocalDate.now()) > 7 ){
            ScholarDTO scholarDTO = ConnectScholar.getScholarInfo(paper.getTitle(), key);
            paper.setScholar(scholarDTO);
            paper.setLastUpdate(LocalDate.now());
            paperMapper.update(paper.getPaperId(), paper);
        }
        return paper;
    }
}
