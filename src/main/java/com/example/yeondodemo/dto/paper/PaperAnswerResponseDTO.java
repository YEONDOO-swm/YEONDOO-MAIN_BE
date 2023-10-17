package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.paper.item.ItemPosition;
import com.example.yeondodemo.dto.python.PythonPaperPosition;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class PaperAnswerResponseDTO {
    private String answer;
    private ItemPosition position;
    public PaperAnswerResponseDTO(PythonQuestionResponse pythonQuestionResponse){
        this.answer  = pythonQuestionResponse.getAnswer();
        this.position = new ItemPosition();
        PythonPaperPosition position = pythonQuestionResponse.getCoordinates();
        this.position.setPageIndex(position.getPage());
        List<List<Float>> rects = List.of(List.of(position.getX0(), position.getY0(), position.getX1(), position.getY1()));
        this.position.setRects(rects);
    }

}
