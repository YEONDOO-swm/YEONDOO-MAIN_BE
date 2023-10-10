package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.PaperInfo;

import java.util.List;

public interface PaperInfoRepository {
    public PaperInfo save(PaperInfo paperInfo);
    public void update(int id, String content);
    public String findByPaperIdAndType(String paperid, String infotype);
    public List<String> findManyByPaperIdAndType(String paperid, String infotype);
}
