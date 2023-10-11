package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.DetailPaperInfo;
import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RetPaperInfoDTO {
    private DetailPaperInfo paperInfo;
    private List<PaperHistory> paperHistory;
    public RetPaperInfoDTO(Paper paper, String summary, List<String> questions, List<PaperHistory> paperHistories, List<ItemAnnotation> paperItems, Boolean isLike){
        this.paperHistory = paperHistories;
        this.paperInfo = new DetailPaperInfo(paper, summary, questions, paperItems, isLike);
    }
}
