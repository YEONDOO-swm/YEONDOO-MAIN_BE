package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.mapper.AuthorMapper;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.utils.Updater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j  @RequiredArgsConstructor @Repository
public class BatisPaperRepository implements PaperRepository {
    private final PaperMapper paperMapper;
    private final Updater updater;
    private final AuthorMapper authorMapper;
    @Override
    public Paper findById(String id) {
        Paper paper = paperMapper.findById(id);
        if(paper==null){
            return null;
        }else{
            if(paper.getVersion() == null){paper.setUrl("https://arxiv.org/abs/" + paper.getPaperId());}
            else{paper.setUrl("https://arxiv.org/abs/" + paper.getPaperId() + "/" +  paper.getVersion());}
            try{
                paper.setYear(2000 + Integer.parseInt(paper.getPaperId().substring(0,2)));
            }catch (Exception e){
                String[] split = paper.getPaperId().split("/");
                int t = Integer.parseInt(split[1].substring(0, 2));
                if(t< 10){
                    paper.setYear(2000+t);
                }else{
                    paper.setYear(1900+t);
                }
            }
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


}
