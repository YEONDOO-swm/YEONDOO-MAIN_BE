package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.paper.item.ItemPosition;
import com.example.yeondodemo.dto.python.PythonPaperPosition;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter @ToString @Slf4j
public class PaperAnswerResponseDTO {
    private String answer;
    private List<ItemPosition> positions;
    public PaperAnswerResponseDTO(){

    }
    public PaperAnswerResponseDTO(PythonQuestionResponse pythonQuestionResponse){
        this.answer  = pythonQuestionResponse.getAnswer();
        this.positions = new ArrayList<>();
        Map<Integer, ItemPosition> map = new HashMap<>();
        List<PythonPaperPosition> positions = pythonQuestionResponse.getCoordinates();
        for (PythonPaperPosition pythonPaperPosition : positions) {
            if(map.get(pythonPaperPosition.getPage())!= null){
                List<Float> rect = List.of(pythonPaperPosition.getX0(), pythonPaperPosition.getY0(), pythonPaperPosition.getX1(), pythonPaperPosition.getY1());
                map.get(pythonPaperPosition.getPage()).getRects().add(rect);
            }else{
                ItemPosition itemPosition = new ItemPosition();
                itemPosition.setPageIndex(pythonPaperPosition.getPage());
                List<List<Float>> rects = new ArrayList<>(List.of(List.of(pythonPaperPosition.getX0(), pythonPaperPosition.getY0(), pythonPaperPosition.getX1(), pythonPaperPosition.getY1())));
                itemPosition.setRects(rects);
                map.put(pythonPaperPosition.getPage(), itemPosition);

            }
        }
        this.positions = map.values().stream().toList();
    }

}
