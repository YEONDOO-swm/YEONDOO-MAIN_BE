package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.workspace.WorkspacePutDTO;
import com.example.yeondodemo.entity.Workspace;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository  {
    public Workspace save(Workspace workspace);
    public List<Workspace> findById(List<Long> workspaceIds);
    public List<Workspace> findAll();
    public Workspace findByName(Long name);
    public void clearStore();
    public WorkspacePutDTO update(WorkspacePutDTO user);
}
