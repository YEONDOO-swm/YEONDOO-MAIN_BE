package com.example.yeondodemo.dto.paper.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DeleteItemDTO {
    private String itemId;
    private Long workspaceId;
    private String paperId;
    public DeleteItemDTO(String itemId, Long workspaceId, String paperId){
        this.itemId = itemId;
        this.workspaceId = workspaceId;
        this.paperId = paperId;
    }
}
