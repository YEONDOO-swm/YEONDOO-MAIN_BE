package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class WorkspacePutDTO {
    private Long workspaceId;
    private String description;
    private String title;
    private String studyField;
    private List<String> keywords=new ArrayList<>();
    private List<Paper> likePapers=new ArrayList<>();
}
