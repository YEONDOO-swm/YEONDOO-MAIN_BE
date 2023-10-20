package com.example.yeondodemo.dto;

import com.example.yeondodemo.dto.paper.Paper4Container;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter @Setter
@ToString
public class LibraryDTO{
    private Long workspaceId;
    private String workspaceTitle;
    private String title;
    private Integer year;
    private String url;
    private String paperId;
    private List<String> authors;
    private String conference;
    private int cites;
}
