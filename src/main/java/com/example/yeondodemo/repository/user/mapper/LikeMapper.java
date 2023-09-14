package com.example.yeondodemo.repository.user.mapper;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface LikeMapper {
    void save(Long workspaceId, String paperId, Boolean isValid, Integer id);
    void update(Long workspaceId, String paperId, Boolean isValid);
    List<String> findByUser(Long workspaceId);
    List<String> findAllByUser(Long workspaceId);
    List<PaperSimpleIdTitleDTO> findSimpleByUser(Long workspaceId);
    List<PaperSimpleIdTitleDTO> findSimpleTrashByUser(Long workspaceId);
    Boolean isLike(Long workspaceId, String paperId);
    void clear();
}
