package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSpaceResponseDTO {
    private Long workspaceId;
    private String description;
    private String title;
    private String studyField;
    private String editDate;
    private List<String> keywords=new ArrayList<>();
    private List<Paper> likePapers=new ArrayList<>();
    //
    public UserSpaceResponseDTO(Workspace workspace){
        this.workspaceId = workspace.getWorkspaceId();
        this.description = workspace.getDescription();
        this.title = workspace.getTitle();
        this.studyField = workspace.getStudyField();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        this.editDate = df.format(workspace.getEditDate());
        this.keywords = workspace.getKeywords();
        this.likePapers = workspace.getLikePapers();
    }

}
