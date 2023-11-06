package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.dto.paper.PaperDTO;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class WorkspaceEnterDTO {
    private List<PaperSimpleIdTitleDTO> recentlyPapers;
    private List<PaperDTO> recommendedPapers; //두개만

    private List<TrendResponseDTO> recentlyTrends;
    public WorkspaceEnterDTO(List<PaperSimpleIdTitleDTO> recentlyPapers, List<PaperDTO> recommendedPapers, List<TrendResponseDTO> recentlyTrends){
        this.recentlyTrends = recentlyTrends;
        this.recommendedPapers = recommendedPapers;
        this.recentlyPapers = recentlyPapers;
    }

}
