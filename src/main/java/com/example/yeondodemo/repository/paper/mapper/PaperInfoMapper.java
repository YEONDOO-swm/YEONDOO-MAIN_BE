package com.example.yeondodemo.repository.paper.mapper;

import com.example.yeondodemo.dto.paper.PaperInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperInfoMapper {
    void save(@Param("paperId") String paperId, @Param("saveParam") PaperInfo paperInfo);
    void update(@Param("id") int id, @Param("updateParam") String content);
    String findByPaperIdAndType(String paperid, String infotype); //info type can be insight, question, subjectrecommend, reference
    List<String> findManyByPaperIdAndType(String paperid, String infotype);
}
