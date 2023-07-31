package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import com.example.yeondodemo.entity.User;

import java.util.List;
import java.util.Set;

public interface LikePaperRepository {
    void save(String username, String paperId, Boolean isValid);
    void update(String username, String paperId, Boolean isValid);
    List<String> findByUser(String username);
    List<String> findAllByUser(String username);
    List<PaperSimpleIdTitleDTO> findSimpleByUser(String username);
    List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(String username);
    void clear();
}
