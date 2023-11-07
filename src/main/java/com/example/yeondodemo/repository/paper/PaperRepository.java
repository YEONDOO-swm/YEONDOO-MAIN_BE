package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;

import java.util.List;

public interface PaperRepository {
    public Paper findById(String id);
    public Paper findById(String id,Boolean update);
    public void clearStore();
    public void update(String id, Paper paper);
    public void save(Paper paper);

    Paper selectRandomReferenceIds(String paperId);
    void add(String id);
    void sub(String id);
    void saveF(PaperFullMeta paperFullMeta);
    Paper findByIdForValid(String id);
    List<String> findAllNullPaperId();
    void saveReferences(List<String> references, String paperid);
    List<PaperSimpleIdTitleDTO> findReferenceById(String paperid, Long workspaceId);

}
