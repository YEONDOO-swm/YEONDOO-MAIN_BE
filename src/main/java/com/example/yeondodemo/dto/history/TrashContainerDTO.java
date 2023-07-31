package com.example.yeondodemo.dto.history;

import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class TrashContainerDTO {
    private List<PaperSimpleIdTitleDTO> trashContainers;
    private List<PaperSimpleIdTitleDTO> papers;
    public TrashContainerDTO(List<PaperSimpleIdTitleDTO> trashContainers, List<PaperSimpleIdTitleDTO> papers){
        this.trashContainers = trashContainers;
        this.papers = papers;
    }
}
