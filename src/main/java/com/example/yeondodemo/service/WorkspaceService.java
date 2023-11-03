package com.example.yeondodemo.service;

import com.example.yeondodemo.dto.PaperDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.dto.workspace.*;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.KeywordRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.batis.BatisRecentlyRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
@FunctionalInterface
interface likeCheck {
    public void check(PaperDTO paper);
}

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceService {
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final RealUserRepository realUserRepository;
    private final JwtTokenProvider provider;
    private final BatisRecentlyRepository recentlyRepository;
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private Long MASK = 9007199254740991l;
    @Transactional
    public void updateWorkspace(WorkspacePutDTO workspace){
        userRepository.update(workspace);
    }
    public WorkspaceEnterDTO getWorkspaceHome(Long workspaceId){
        //todo: 추천 로직 완료시 이부분 로직 짜기.
        List<PaperSimpleIdTitleDTO> paperSimpleIdTitleDTOS = recentlyRepository.find3byWorkspaceId(workspaceId);
        List<PaperDTO> reccommendPapers = new ArrayList<>();
        List<TrendResponseDTO> recentlyTrends = new ArrayList<>();



        TrendResponseDTO trendResponseDTO1 = new TrendResponseDTO("forTest",LocalDate.of(1999,8,22), "test.test1");
        TrendResponseDTO trendResponseDTO2 = new TrendResponseDTO("forTest",LocalDate.of(2023,10,03), "test.test2");
        TrendResponseDTO trendResponseDTO3 = new TrendResponseDTO("forTest",LocalDate.of(2029,10,22), "test.test3");
        recentlyTrends.add(trendResponseDTO1);
        recentlyTrends.add(trendResponseDTO2);
        recentlyTrends.add(trendResponseDTO3);

        List<String> userSet = likePaperRepository.findByUser(workspaceId);

        likeCheck isLike = (PaperDTO paper) -> {
            if(userSet.contains(paper.getPaperId())){
                paper.setIsLike(true);
            }
        };



        Paper paper = paperRepository.findById("1706.03762");
        PaperDTO paperDTO = new PaperDTO(paper);
        isLike.check(paperDTO);
        reccommendPapers.add(paperDTO);

        paper = paperRepository.findById("1706.03761");
        PaperDTO paperDTO2 = new PaperDTO(paper);
        isLike.check(paperDTO2);
        reccommendPapers.add(paperDTO2);

        paper = paperRepository.findById("1706.03763");
        PaperDTO paperDTO3 = new PaperDTO(paper);
        isLike.check(paperDTO3);
        reccommendPapers.add(paperDTO3);


        WorkspaceEnterDTO workspaceEnterDTO = new WorkspaceEnterDTO(paperSimpleIdTitleDTOS, reccommendPapers, recentlyTrends);
        return workspaceEnterDTO;
    }
    public Long makeWorkspaceId(){
        return UUID.randomUUID().getMostSignificantBits() & MASK;
    }
    public UserSpaceResponseDTO getUserSpaces(String jwt){
        Set<Long> workspaceList = WorkspaceValidator.login.get(jwt);
        List<UserSpaceDTO> workspaces = new ArrayList<>();

        for(Workspace workspace: userRepository.findById(new ArrayList<>(workspaceList))){
            List<String> keywords = keywordRepository.findByUsername(workspace.getWorkspaceId());
            workspace.setKeywords(keywords);
            workspaces.add(new UserSpaceDTO(workspace));
        }
        return new UserSpaceResponseDTO(workspaces);
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
    public Map setWorkspace(String jwt, Workspace workspace){
        Long key = makeWorkspaceId();
        workspace.setWorkspaceId(key);
        String email = provider.getUserName(jwt);
        realUserRepository.saveWorkspace(email, workspace);
        keywordRepository.save(key, workspace.getKeywords());
        Map ret = new HashMap<>();
        ret.put("workspaceId", key);

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String formatted = df.format(new Date());
        ret.put("editDate", formatted);

        WorkspaceValidator.login.get(jwt).add(key);
        log.info("long sate: {}", WorkspaceValidator.login.get(jwt).toString());
        return ret;
    }
    @Transactional
    public void updateWorkspaceValidity(String jwt, Long workspaceId){
        realUserRepository.updateWorkspaceValidity(workspaceId);
        WorkspaceValidator.login.get(jwt).remove(workspaceId);
    }
}
