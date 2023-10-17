package com.example.yeondodemo.dto.paper.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExportItemDTO {
    private List<ItemAnnotation> annotations;
    private String fileFormat;
    private String purpose;
}
