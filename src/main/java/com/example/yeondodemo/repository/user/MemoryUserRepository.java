package com.example.yeondodemo.repository.user;


import com.example.yeondodemo.dto.workspace.WorkspacePutDTO;
import com.example.yeondodemo.entity.Workspace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
public class MemoryUserRepository implements UserRepository {
    private static final Map<Long, Workspace> store = new HashMap<>();
    private static final Map<Long, Long> name2id = new HashMap<>();
    private static Long sequence = 0L;
    @Override
    public List<Workspace> findById(List<Long> id) {
        log.info("findByID: {}", id);
        return null;
    }
    @Override
    public Workspace save(Workspace user){
        log.info("save User {}", user);
        store.put(++sequence, user);
        user.setId(sequence);
        name2id.put(user.getWorkspaceId(), sequence);
        return user;
    }
    @Override
    public List<Workspace> findAll() {
        log.info("findAll");
        return new ArrayList<Workspace>(store.values());
    }

     @Override
     public Workspace findByName(Long name) {
         return null;
     }

    public Workspace findByName(String name) {
        log.info("findByName name: {}", name);
        return store.get(name2id.get(name));
    }

    @Override
    public void clearStore() {
        store.clear();
        name2id.clear();
    }
    @Override
    public WorkspacePutDTO update(WorkspacePutDTO user){
        return user;
    }

    @Override
    public void updateDate(Long workspaceId) {

    }
}
