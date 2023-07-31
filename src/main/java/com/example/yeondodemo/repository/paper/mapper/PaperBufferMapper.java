package com.example.yeondodemo.repository.paper.mapper;

import com.example.yeondodemo.dto.BufferUpdateDTO;
import com.example.yeondodemo.dto.PaperBuffer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PaperBufferMapper {
    void save(PaperBuffer paperBuffer);
    void update(@Param("id") String id, @Param("updateParam")BufferUpdateDTO bufferUpdateDTO);
    PaperBuffer findById(String id);
    Boolean isHit(String id);
}
