package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.entity.Keywords;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.service.WorkspaceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString @RequiredArgsConstructor
public class GetWorkspaceMetaDTO {
    private Long workspaceId;
    private String title;
    private String description;
    private List<String> studyFieldList;
    private String studyField;
    private List<String> keywords;

    public GetWorkspaceMetaDTO(Workspace workspace, List<String> studyFieldList){
        this.workspaceId = workspace.getWorkspaceId();
        this.title = workspace.getTitle();
        this.description = workspace.getDescription();
        this.studyFieldList = studyFieldList;
        this.studyField = workspace.getStudyField();
        this.keywords = workspace.getKeywords();
    }
    public GetWorkspaceMetaDTO(Long workspaceId,List<String> studyFieldList){
        this.workspaceId = workspaceId;
        this.studyFieldList = studyFieldList;
    }
}
