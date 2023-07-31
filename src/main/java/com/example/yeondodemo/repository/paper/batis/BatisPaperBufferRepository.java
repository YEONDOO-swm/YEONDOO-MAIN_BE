package com.example.yeondodemo.repository.paper.batis;


import com.example.yeondodemo.dto.BufferUpdateDTO;
import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.repository.paper.mapper.PaperBufferMapper;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j @RequiredArgsConstructor @Repository
public class BatisPaperBufferRepository implements PaperBufferRepository {
    private final PaperBufferMapper paperBufferMapper;
    @Override

    public PaperBuffer save(PaperBuffer paperBuffer){
        log.info("save PaperBuffer {}", paperBuffer);
        paperBufferMapper.save(paperBuffer);
        return paperBuffer;
    }
    @Override
    public void update(String id, BufferUpdateDTO bufferUpdateDTO){
        log.info("update paper id: {}, conent: {}", id, bufferUpdateDTO);
        paperBufferMapper.update(id, bufferUpdateDTO);
    }
    @Override
    public PaperBuffer findById(String id){
        log.info("find by ID({})");
        return paperBufferMapper.findById(id);
    }
    @Override
    public Boolean isHit(String id){
        log.info("isHit ID({})");
        return paperBufferMapper.isHit(id);

    }
}
