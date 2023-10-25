package com.example.yeondodemo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter @Getter @ToString(exclude = "id")
public class Workspace {
    private Long id;
    private Long workspaceId;
    private String description;
    private String title;
    private String studyField;
    private Date editDate;
    private Date editDateTime;
    private List<String> keywords=new ArrayList<>();
    private List<Paper> likePapers=new ArrayList<>();
    public Workspace(Long workspaceId, String description){
        this.workspaceId=workspaceId;
        this.description=description;
    }
    public Workspace(Long workspaceId, String description, String title){
        this.workspaceId=workspaceId;
        this.description=description;
        this.title= title;
    }
    public Workspace() {

    }
}
