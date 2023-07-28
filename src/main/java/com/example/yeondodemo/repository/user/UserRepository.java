package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  {
    public User save(User user);
    public User findById(Long id);
    public List<User> findAll();
    public User findByName(String name);
    public void clearStore();
    public User update(User user);
}
