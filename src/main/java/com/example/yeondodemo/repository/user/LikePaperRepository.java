package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;

import java.util.List;
import java.util.Set;

public interface LikePaperRepository {
    void save(Long workspaceId, String paperId, Boolean isValid);
    void update(Long workspaceId, String paperId, Boolean isValid);
    List<String> findByUser(Long workspaceId);
    List<String> findAllByUser(Long workspaceId);
    List<PaperSimpleIdTitleDTO> findSimpleByUser(Long workspaceId);
    List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(Long workspaceId);
    Boolean isLike(Long workspaceId, String paperId);
    void updateDate(Long workspaceId, String paperId);
    void clear();
}
