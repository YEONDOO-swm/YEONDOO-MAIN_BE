package com.example.yeondodemo.repository.paper.mapper;

import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.dto.token.TokenUsageDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperMapper {
    void setToken(Integer token);
    int getToken();
    public Paper findById(String id);
    public void clearStore();
    public void update(@Param("id") String id, @Param("updateParam") Paper paper);
    public void save(Paper paper);
    Paper selectRandomReferenceIds(String paperId);
    public void updateSummary(String id, String summary);
    String findSummaryById(String id);
    void add(String id);
    void sub(String id);
    void saveF(PaperFullMeta paperFullMeta);
    List<String> findAllNullPaperId();
    void saveReferences(List<String> references, String paperid);
    void saveReference(String reference, String paperid);
    List<PaperSimpleIdTitleDTO> findReferenceById(String paperid, Long workspaceId);
    int findLeftQuestionsById(String email);

    void saveUsage(TokenUsageDTO tokenDTO);

    TokenUsageDTO getUsageByEmail(String email);
}
