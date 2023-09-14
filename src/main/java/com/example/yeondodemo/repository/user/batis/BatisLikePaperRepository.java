package com.example.yeondodemo.repository.user.batis;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.mapper.LikeMapper;
import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisLikePaperRepository implements LikePaperRepository {
    private final LikeMapper likeMapper;
    @Value("${python.address}") private String pythonapi;
    @Override
    public void save(Long workspaceId, String paperId, Boolean isValid) {
        likeMapper.save(workspaceId , paperId, isValid, null);
    }

    @Override
    public void update(Long workspaceId, String paperId, Boolean isValid) {
        likeMapper.update(workspaceId, paperId, isValid);
    }

    @Override
    public List<String> findByUser(Long workspaceId) {
        return  likeMapper.findByUser(workspaceId);
    }

    @Override
    public List<String> findAllByUser(Long workspaceId) {
        return likeMapper.findAllByUser(workspaceId);
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findSimpleByUser(Long workspaceId) {
        return likeMapper.findSimpleByUser(workspaceId);
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(Long workspaceId) {
        return likeMapper.findSimpleTrashByUser(workspaceId);
    }

    @Override
    public Boolean isLike(Long workspaceId, String paperId) {
        return likeMapper.isLike(workspaceId, paperId);
    }

    @Override
    public void clear() {
        likeMapper.clear();
    }
}
