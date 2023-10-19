package com.example.yeondodemo.repository.etc.batis;

import com.example.yeondodemo.dto.LibraryDTO;
import com.example.yeondodemo.repository.etc.mapper.AuthorMapper;
import com.example.yeondodemo.repository.etc.mapper.LibraryMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatisLibraryRepository {
    private final LibraryMapper libraryMapper;
    private final AuthorMapper authorMapper;
    public List<LibraryDTO> findByUserName(String username){
        List<LibraryDTO> ret = libraryMapper.findByUserName(username);
        for (LibraryDTO libraryDTO : ret) {
            List<String> authors = authorMapper.findByPaperId(libraryDTO.getPaperId());
            libraryDTO.setAuthors(authors);
        }
        return ret;
    }
}
