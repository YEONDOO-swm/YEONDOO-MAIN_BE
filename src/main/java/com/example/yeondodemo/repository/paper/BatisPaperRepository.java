package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j @Repository @RequiredArgsConstructor
public class BatisPaperRepository implements PaperRepository {
    private final PaperMapper paperMapper;
    @Override
    public Paper findById(String id) {
        log.info("findById id: {}", id);
        return paperMapper.findById(id);
    }


    @Override
    public void clearStore() {
        log.info("clear paper repository");
        paperMapper.clearStore();
    }
    @Override
    public void update(String id, Paper paper){
        log.info("update paper");
        paperMapper.update(id, paper);
    }

    @Override
    public void save(Paper paper) {
        log.info("save Paper : {}", paper);
        paperMapper.save(paper);
    }
    @Override
    public void updateSummary(String id, String summary){
        paperMapper.updateSummary(id, summary);
    }

    @Override
    public Paper findFullById(String id) {
        return paperMapper.findFullById(id);
    }

    @Override
    public String findSummaryById(String id) {
        return paperMapper.findSummaryById(id);
    }


}
