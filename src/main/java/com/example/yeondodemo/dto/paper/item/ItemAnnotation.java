package com.example.yeondodemo.dto.paper.item;

import jakarta.validation.constraints.Size;
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
    private String pageLabel;
    @Size(max = 65535)
    private String text;
    private long workspaceId;
    @Size(max = 65535)
    private String comment;
    private ItemPosition position;
    private String positionString;
    private Date dateCreated;
    private Date dateModified;
    private String sortIndex;
}
