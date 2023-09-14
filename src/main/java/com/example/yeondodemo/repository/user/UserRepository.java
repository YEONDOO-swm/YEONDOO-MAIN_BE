package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.entity.Workspace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  {
    public Workspace save(Workspace workspace);
    public Workspace findById(Long id);
    public List<Workspace> findAll();
    public Workspace findByName(Long name);
    public void clearStore();
    public Workspace update(Workspace user);
}
