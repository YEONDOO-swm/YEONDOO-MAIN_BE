package com.example.yeondodemo.repository.user.mapper;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface LikeMapper {
    void save(String username, String paperId, Boolean isValid, Integer id);
    void update(String username, String paperId, Boolean isValid);
    List<String> findByUser(String username);
    List<String> findAllByUser(String username);
    List<PaperSimpleIdTitleDTO> findSimpleByUser(String username);
    List<PaperSimpleIdTitleDTO> findSimpleTrashByUser(String username);
    void clear();
}
