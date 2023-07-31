package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.repository.etc.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @RequiredArgsConstructor @Slf4j
public class BatisAuthorRepository {
    private final AuthorMapper authorMapper;
    public void save(String paperId, String author){
        authorMapper.save(paperId, author);
    }
    public void saveAll(String paperId, List<String> authors){
        authorMapper.saveAll(paperId, authors);
    }
    public List<String> findByPaperId(String paperId){
        return authorMapper.findByPaperId(paperId);
    }
}
