package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.mapper.AuthorMapper;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.utils.Updater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j  @RequiredArgsConstructor @Repository
public class BatisPaperRepository implements PaperRepository {
    private final PaperMapper paperMapper;
    private final Updater updater;
    private final AuthorMapper authorMapper;
    @Override
    public Paper findByIdForValid(String id) {
        return paperMapper.findById(id);
    }

    @Override
    public List<String> findAllNullPaperId() {
        return paperMapper.findAllNullPaperId();
    }

    @Override
    public Paper findById(String id) {
        Paper paper = paperMapper.findById(id);
        if(paper==null){
            return null;
        }else{
            paper.setAuthors(authorMapper.findByPaperId(paper.getPaperId()));
            //paper = updater.update(paper);
        }
        return paper;
    }

    @Override
    public Paper findById(String id, Boolean update) {
        Paper paper = paperMapper.findById(id);
        return paper;
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
        paperMapper.add(id);
    }

    @Override
    public void sub(String id) {
        paperMapper.sub(id);
    }

    @Override
    public void saveF(PaperFullMeta paperFullMeta) {
        paperMapper.saveF(paperFullMeta);
    }

    @Override
    public void saveReferences(List<String> references, String paperid){
        List<String> notInList = new ArrayList<>();
        for (String reference : references) {
            try{
                paperMapper.saveReference(reference, paperid);
            }catch (Exception e){
                if(e.getClass()==SQLIntegrityConstraintViolationException.class || e.getClass() == DataIntegrityViolationException.class){
                    notInList.add(reference);
                }
                System.out.println(e.getClass());
            }
        }
        if(!notInList.isEmpty()){
            log.info("There is None - Registered Paper.., {}", notInList);
            List<Paper> paperMeta = updater.getPaperMeta(notInList);
            for (Paper paper : paperMeta) {
                authorMapper.saveAll(paper.getPaperId(), paper.getAuthors());
                paperMapper.saveReference(paper.getPaperId(), paperid);
            }
        }
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findReferenceById(String paperid) {
        return paperMapper.findReferenceById(paperid);
    }


}
