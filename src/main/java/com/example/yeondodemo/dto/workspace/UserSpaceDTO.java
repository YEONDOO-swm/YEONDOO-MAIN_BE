package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserSpaceDTO {
    private Long workspaceId;
    private String description;
    private String title;
    private String studyField;
    private String editDate;
    private List<String> keywords;
    private List<Paper> likePapers;
    public UserSpaceDTO(Workspace workspace){
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
