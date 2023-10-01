package com.example.yeondodemo.repository.paper.batis;


import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.repository.paper.mapper.RecentlyPaperMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BatisRecentlyRepository {
    private final RecentlyPaperMapper recentlyPaperMapper;
    public void save(String paperId, Long workspaceId){
        if(recentlyPaperMapper.findbyworkspaceIdAndPaperId(workspaceId, paperId) ==null){
            recentlyPaperMapper.save(workspaceId, paperId);
        }else{
            recentlyPaperMapper.updateByWorkspaceIdAndPaperId(workspaceId,paperId);
        }
    }
    public List<PaperSimpleIdTitleDTO> find3byWorkspaceId(Long workspaceId){
        return recentlyPaperMapper.find3byworkspaceId(workspaceId);
    }
}
