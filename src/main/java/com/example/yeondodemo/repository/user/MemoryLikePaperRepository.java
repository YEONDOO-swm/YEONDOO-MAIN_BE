package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import com.example.yeondodemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryLikePaperRepository implements LikePaperRepository{
    private static final Map<String, Set<String>> store = new HashMap<>();

    @Override
    public void save(String username, String paperId, Boolean isValid) {
        if(store.get(username)==null){store.put(username,new HashSet<>());}
            if(isValid) {
                store.get(username).add(paperId);
            }
            else {
                store.remove(paperId);
            }
    }
    @Override
    public  void update(String username, String paperId, Boolean isValid){

    }

    @Override
    public List<String> findByUser(String username) {
        return null;
    }

    @Override
    public List<String> findAllByUser(String username) {
        return null;
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findSimpleByUser(String username) {
        return null;
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(String username) {
        return null;
    }

    @Override
    public void clear() {
        store.clear();
    }
}
