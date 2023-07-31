package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.repository.user.mapper.LikeMapper;
import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisLikePaperRepository implements LikePaperRepository{
    private final LikeMapper likeMapper;
    @Value("${python.address}") private String pythonapi;
    @Override
    public void save(String username, String paperId, Boolean isValid) {
        likeMapper.save(username, paperId, isValid, null);
    }

    @Override
    public void update(String username, String paperId, Boolean isValid) {
        likeMapper.update(username, paperId, isValid);
    }

    @Override
    public List<String> findByUser(String username) {
        return  likeMapper.findByUser(username);
    }

    @Override
    public List<String> findAllByUser(String username) {
        return likeMapper.findAllByUser(username);
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findSimpleByUser(String username) {
        return likeMapper.findSimpleByUser(username);
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(String username) {
        return likeMapper.findSimpleTrashByUser(username);
    }

    @Override
    public void clear() {
        likeMapper.clear();
    }
}
