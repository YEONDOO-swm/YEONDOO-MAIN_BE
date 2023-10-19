package com.example.yeondodemo.repository.etc.mapper;

import com.example.yeondodemo.dto.LibraryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LibraryMapper{
    public List<LibraryDTO> findByUserName(String username);
}
