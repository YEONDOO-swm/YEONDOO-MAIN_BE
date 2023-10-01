package com.example.yeondodemo.repository.paper.mapper;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface RecentlyPaperMapper {
    void save(Long workspaceId, String paperId);
    List<PaperSimpleIdTitleDTO> find3byworkspaceId(Long workspaceId);
    void updateByWorkspaceIdAndPaperId(Long workspaceId, String paperId);
    String findbyworkspaceIdAndPaperId(Long workspaceId, String paperId);
}
