package com.example.yeondodemo.repository.user.mapper;

import com.example.yeondodemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public void save(@Param("saveParam")User user);
    public User findByName(String username);
    public void clearStore();
    public void update(@Param("updateParam") User user);
}
