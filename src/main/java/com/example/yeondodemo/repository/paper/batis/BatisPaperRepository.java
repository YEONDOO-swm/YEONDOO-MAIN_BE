package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j  @RequiredArgsConstructor //@Repository
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
    public void add(String id) {

    }

    @Override
    public void sub(String id) {

    }


}
