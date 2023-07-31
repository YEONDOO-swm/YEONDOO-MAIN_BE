package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.dto.PaperInfo;
import com.example.yeondodemo.repository.paper.mapper.PaperInfoMapper;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BatisPaperInfoRepository implements PaperInfoRepository {
    private final PaperInfoMapper paperInfoMapper;
    @Override
    public PaperInfo save(PaperInfo paperInfo){
        log.info("save PaperInfo: {}", paperInfo);
        paperInfoMapper.save(paperInfo.getPaperid(), paperInfo);
        return paperInfo;
    }
    @Override
    public void update(int id, String content){
        log.info("update paper info id: {}, content: {}", id, content);
        paperInfoMapper.update(id, content);
    }

    @Override
    public List<String> findByPaperIdAndType(String paperid, String infotype){
        log.info("findbypaperidandtype, paperid: {} , type: {}", paperid, infotype);
        return paperInfoMapper.findByPaperIdAndType(paperid, infotype);
    }
}
