package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.Paper4Container;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class LibraryDTO extends Paper4Container {
    private Long workspaceId;
    private String workspaceTitle;
    public LibraryDTO(Paper paper, Long workspaceId, String title) {
        super(paper);
        this.workspaceId = workspaceId;
        this.workspaceTitle = title;
    }
}
