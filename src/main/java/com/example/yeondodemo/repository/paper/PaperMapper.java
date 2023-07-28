package com.example.yeondodemo.repository.paper;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperMapper {
    public Paper findById(String id);
    public void clearStore();
    public void update(@Param("id") String id, @Param("updateParam") Paper paper);
    public void save(Paper paper);
    public void updateSummary(String id, String summary);
    public Paper findFullById(String id);
    String findSummaryById(String id);

}
