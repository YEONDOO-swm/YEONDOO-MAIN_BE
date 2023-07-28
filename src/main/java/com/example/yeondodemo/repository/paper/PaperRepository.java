package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;

import java.util.List;

public interface PaperRepository {
    public Paper findById(String id);
    public void clearStore();
    public void update(String id, Paper paper);
    public void save(Paper paper);
    public void updateSummary(String id, String summary);
    public Paper findFullById(String id);
    public String findSummaryById(String id);

}
