package com.example.yeondodemo.validation;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import com.example.yeondodemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class PaperValidator {
    private static PaperRepository paperRepository;
    private static UserRepository userRepository;
    private  static LikePaperRepository likePaperRepository;
    public static void init(ApplicationContext context){
        paperRepository = context.getBean(PaperRepository.class);
        userRepository = context.getBean(UserRepository.class);
        likePaperRepository = context.getBean(LikePaperRepository.class);
    }
    public static boolean isValidPaper(String id){
        boolean valid = Optional.ofNullable(paperRepository.findById(id)).isPresent();
        log.info("Paper: {} is {}", id, valid);
        return Optional.ofNullable(paperRepository.findById(id)).isPresent();
    }
    public static boolean isValidOnOff(String username, String id, boolean onoff){
        User user = userRepository.findByName(username);
        Paper paper = paperRepository.findById(id);
        List<String> userSet = likePaperRepository.findByUser(user.getUsername());
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
}
