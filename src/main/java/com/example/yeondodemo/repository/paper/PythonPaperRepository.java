package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.utils.ConnectPythonServer;
import com.example.yeondodemo.utils.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor //@Repository
public class PythonPaperRepository implements PaperRepository{
    private final PaperMapper paperMapper;
    @Value("${python.address}") private String pythonapi;
    private final Updater updater;
    @Override
    public Paper findById(String id) {
        TestPython testPython = ConnectPythonServer.getMeta(id, pythonapi);
        if(testPython == null){return null;}
        Paper paper = paperMapper.findById(id);
        if(paper==null){
            paper = new Paper(id);
            paper.setMeta(testPython);
            paper = updater.save(paper);
        }else{
            paper.setMeta(testPython);
            paper = updater.update(paper);
        }
        return paper;
    }

    @Override
    public Paper findById(String id, Boolean update) {
        return null;
    }

    @Override
    public void clearStore() {

    }

    @Override
    public void update(String id, Paper paper) {
        paperMapper.update(id, paper);
    }

    @Override
    public void save(Paper paper) {
        paperMapper.save(paper);
    }

    @Override
    public Paper selectRandomReferenceIds(String paperId) {
        return null;
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

    }

    public Paper findByIdForValid(String id){return null;}

    @Override
    public List<String> findAllNullPaperId() {
        return null;
    }

    @Override
    public void saveReferences(List<String> references, String paperid) {

    }

    @Override
    public List<PaperSimpleIdTitleDTO> findReferenceById(String paperid) {
        return null;
    }

}
