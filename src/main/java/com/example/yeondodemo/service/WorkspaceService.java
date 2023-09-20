package com.example.yeondodemo.service;

import com.example.yeondodemo.dto.workspace.GetWorkspaceMetaDTO;
import com.example.yeondodemo.dto.workspace.WorkspaceEnterDTO;
import com.example.yeondodemo.entity.Keywords;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.KeywordRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceService {
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final RealUserRepository realUserRepository;
    private final JwtTokenProvider provider;
    @Transactional
    public void updateWorkspace(Workspace workspace){
        userRepository.update(workspace);
    }
    public WorkspaceEnterDTO getWorkspaceHome(Long workspaceId){
        //todo: 추천 로직 완료시 이부분 로직 짜기.
        WorkspaceEnterDTO workspaceEnterDTO = new WorkspaceEnterDTO();
        return workspaceEnterDTO;
    }
    public Long makeWorkspaceId(){
        while(true){
            Long key = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            if(userRepository.findByName(key) == null){
                return key;
            }
        }
    }
    public List<Workspace> getUserSpaces(String jwt){
        Set<Long> workspaceList = WorkspaceValidator.login.get(jwt);
        return userRepository.findById(new ArrayList<>(workspaceList));
    }

    public GetWorkspaceMetaDTO getWorkspaceMeta(Long workspaceId){
        Workspace workspace;
        if(workspaceId==null){
            workspace = new Workspace();
        }else{
            workspace = userRepository.findByName(workspaceId);
            workspace.setKeywords(keywordRepository.findByUsername(workspaceId));
        }
        List<String> studyfields = studyFieldRepository.findAll();
        return new GetWorkspaceMetaDTO(workspace, studyfields);

    }

    @Transactional
    public Map<String, Long> setWorkspace(String jwt, Workspace workspace){
        Long key = makeWorkspaceId();
        workspace.setWorkspaceId(key);
        String email = provider.getUserName(jwt);
        realUserRepository.saveWorkspace(email, workspace);
        keywordRepository.save(key, workspace.getKeywords());
        Map<String, Long> ret = new HashMap<>();
        ret.put("workspaceId", key);
        WorkspaceValidator.login.get(jwt).add(key);
        return ret;
    }
    @Transactional
    public void updateWorkspaceValidity(String jwt, Long workspaceId){
        realUserRepository.updateWorkspaceValidity(workspaceId);
        WorkspaceValidator.login.get(jwt).remove(workspaceId);
    }
}