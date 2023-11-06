package com.example.yeondodemo.validation;

import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;
@Slf4j
public class PaperValidator {
    private static PaperRepository paperRepository;
    private static UserRepository userRepository;
    private  static LikePaperRepository likePaperRepository;
    private static SearchHistoryRepository searchHistoryRepository;
    private static QueryHistoryRepository queryHistoryRepository;
    public static void init(ApplicationContext context){
        paperRepository = context.getBean(PaperRepository.class);
        userRepository = context.getBean(UserRepository.class);
        likePaperRepository = context.getBean(LikePaperRepository.class);
        searchHistoryRepository = context.getBean(SearchHistoryRepository.class);
        queryHistoryRepository = context.getBean(QueryHistoryRepository.class);
    }
    public static boolean isNotValidHomeResultId(Long workspaceId, PaperResultRequest paperResultRequest){
        return searchHistoryRepository.findByUsernameAndId(workspaceId, paperResultRequest.getId()) == null;
    }
    public static boolean isNotValidResultId(Long workspaceId, PaperResultRequest paperResultRequest){
        return queryHistoryRepository.findByUsernameAndId(workspaceId, paperResultRequest.getId()) == null;
    }


    public static boolean isValidPaper(String id){
        log.info("validity check {}", id);
        return Optional.ofNullable(paperRepository.findByIdForValid(id)).isPresent();
    }
    public static boolean isValidOnOff(Long workspaceId, String id, boolean onoff){
        Workspace workspace = userRepository.findByName(workspaceId);
        Paper paper = paperRepository.findByIdForValid(id);
        List<String> userSet = likePaperRepository.findByUser(workspace.getWorkspaceId());
        if(onoff){
            if(userSet!=null){
                if(userSet.contains(paper.getPaperId())){return false;}
            }
            return true;
        }else{
            if(userSet!=null){
                if(userSet.contains(paper.getPaperId())){return true;}
            }
            return false;
        }
    }
    public static boolean isNotValidRid(Long workspaceId, Long resultId){
        return searchHistoryRepository.findByRidAndUsername(workspaceId, resultId) == null;
    }
    public static boolean isNotValidRestorePapers(Long workspaceId, List<String> papers){
        if(papers.size()==0){return true;}
        for (String paper : papers) {
            log.info("--- paper: " + paper);
            if(!isValidPaper(paper)){return true;}
            if(!isValidOnOff(workspaceId, paper, true)){return true;}
        }
        return false;
    }
}
