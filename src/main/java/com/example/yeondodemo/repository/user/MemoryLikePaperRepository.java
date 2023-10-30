package com.example.yeondodemo.repository.user;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryLikePaperRepository implements LikePaperRepository{
    private static final Map<Long, Set<String>> store = new HashMap<>();

    @Override
    public void save(Long workspaceId, String paperId, Boolean isValid) {
        if(store.get(workspaceId)==null){store.put(workspaceId,new HashSet<>());}
            if(isValid) {
                store.get(workspaceId).add(paperId);
            }
            else {
                store.remove(paperId);
            }
    }
    @Override
    public  void update(Long workspaceId, String paperId, Boolean isValid){

    }

    @Override
    public List<String> findByUser(Long workspaceId) {
        return null;
    }

    @Override
    public List<String> findAllByUser(Long workspaceId) {
        return null;
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findSimpleByUser(Long workspaceId) {
        return null;
    }

    @Override
    public List<PaperSimpleIdTitleDTO> findTrashSimpleByUser(Long workspaceId) {
        return null;
    }

    @Override
    public Boolean isLike(Long workspaceId, String paperId) {
        return null;
    }

    @Override
    public void updateDate(Long workspaceId, String paperId) {

    }


    @Override
    public void clear() {
        store.clear();
    }
}
