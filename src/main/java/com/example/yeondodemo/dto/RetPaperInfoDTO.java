package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.DetailPaperInfo;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RetPaperInfoDTO {
    private DetailPaperInfo paperinfo;
    private List<PaperHistory> paperHistory;
    public RetPaperInfoDTO(Paper paper, PythonPaperInfoDTO pythonPaperInfoDTO, List<PaperHistory> paperHistories){
        this.paperHistory = paperHistories;
        this.paperinfo = new DetailPaperInfo(paper, pythonPaperInfoDTO);
    }
}
