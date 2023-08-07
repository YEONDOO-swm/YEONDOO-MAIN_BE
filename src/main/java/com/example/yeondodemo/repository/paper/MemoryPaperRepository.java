package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MemoryPaperRepository implements PaperRepository {
    private static final Map<String, Paper> store=new HashMap<>();
    @Override
    public Paper findById(String id) {
        log.info("findById id: {}", id);
        return store.get(id);
    }

    @Override
    public Paper findById(String id, Boolean update) {
        return null;
    }


    @Override
    public void clearStore() {
        store.clear();
    }
    @Override
    public void update(String id, Paper paper){

    }

    @Override
    public void  save(Paper paper) {
        log.info("save Paper : {}", paper);
        store.put(paper.getPaperId(), paper);
    }

    @Override
    public void add(String id) {

    }

    @Override
    public void sub(String id) {

    }

    @Override
    public void saveF(PaperFullMeta paperFullMeta) {

    }


}
