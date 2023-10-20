package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.item.ItemPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter @Slf4j @Setter @ToString
public class PythonQuestionDTO {
    private String paperId;
    private String question;
    private List<List<String>> history;
    private String extraPaperId;
    private String underline;
    private ItemPosition position;
    private String key;
    public PythonQuestionDTO(String paperid, QuestionDTO query, List<PaperHistory> paperHistories){
        this.history = new ArrayList<>();
        this.position = query.getPosition();
        this.key = query.getKey();
        List<String> t = null;
        for (PaperHistory paperHistory : paperHistories) {
            if(paperHistory.isWho()){
                t = new ArrayList<>();
            }
            t.add(paperHistory.getContent());
            if(!paperHistory.isWho()){
                history.add(t);

            }
        }
        //구분이 필요.
        this.paperId = paperid;
        this.question = query.getQuestion();
        this.underline = query.getContext();
        for (String s : query.getPaperIds()) {
            if(!paperId.equals(s)){
                this.extraPaperId = s;
                break;
            }
        }
    }
}
