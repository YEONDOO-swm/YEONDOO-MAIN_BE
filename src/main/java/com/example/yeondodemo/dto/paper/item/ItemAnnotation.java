package com.example.yeondodemo.dto.paper.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter @Setter @ToString
public class ItemAnnotation {
    private Long id;
    private String itemId;
    private String itemType;
    private String paperId;
    private String color;
    private String text;
    private long workspaceId;
    private String comment;
    private String pageLabel;
    private ItemPosition position;
    private String positionString;
    private Date dateCreated;
    private Date dateModified;
}
