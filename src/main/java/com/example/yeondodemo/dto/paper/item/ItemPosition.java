package com.example.yeondodemo.dto.paper.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemPosition {
    private Integer pageIndex;
    private List<List<Float>> rects;
    private int fontSize;
    private Integer rotation;
    private List<List<Float>> paths;
    private int width;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        if (pageIndex != null) {
            rootNode.put("pageIndex", pageIndex);
        }
        if (rects != null && !rects.isEmpty()) {
            // rects가 null이 아니고 비어있지 않을 경우에만 추가
            rootNode.set("rects", objectMapper.valueToTree(rects));
        }
        if (fontSize != 0) {
            rootNode.put("fontSize", fontSize);
        }
        if (rotation != null) {
            rootNode.put("rotation", rotation);
        }
        if (paths != null && !paths.isEmpty()) {
            // paths가 null이 아니고 비어있지 않을 경우에만 추가
            rootNode.set("paths", objectMapper.valueToTree(paths));
        }
        if (width != 0) {
            rootNode.put("width", width);
        }
        return rootNode.toString();
    }

}

