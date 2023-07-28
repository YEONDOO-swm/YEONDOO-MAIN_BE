package com.example.yeondodemo.repository.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository @Slf4j @RequiredArgsConstructor
public class BatisLikePaperRepository implements LikePaperRepository{
    private final LikeMapper likeMapper;
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
    public void clear() {
        likeMapper.clear();
    }
}
