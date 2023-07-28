package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.dto.BufferUpdateDTO;
import com.example.yeondodemo.dto.PaperBuffer;

public interface PaperBufferRepository {

    public PaperBuffer save(PaperBuffer paperBuffer);
    public void update(String id, BufferUpdateDTO bufferUpdateDTO);
    public PaperBuffer findById(String id);
    public Boolean isHit(String id);
}
