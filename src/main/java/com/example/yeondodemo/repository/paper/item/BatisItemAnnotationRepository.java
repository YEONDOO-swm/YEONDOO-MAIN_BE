package com.example.yeondodemo.repository.paper.item;

import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.dto.paper.item.DeleteItemDTO;
import com.example.yeondodemo.dto.paper.item.ItemPosition;
import com.example.yeondodemo.repository.paper.item.mapper.ItemAnnotationMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BatisItemAnnotationRepository {
    private final ItemAnnotationMapper itemAnnotationMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public long save(ItemAnnotation itemAnnotation){
        itemAnnotationMapper.save(itemAnnotation);
        return itemAnnotation.getId();
    }
    public void update(ItemAnnotation itemAnnotation){
        itemAnnotationMapper.update(itemAnnotation);
    }
    public List<ItemAnnotation> findByPaperIdAndWorkspaceId(String paperId, Long workspaceId) throws JsonProcessingException {
        List<ItemAnnotation> byPaperIdAndWorkspaceId = itemAnnotationMapper.findByPaperIdAndWorkspaceId(paperId, workspaceId);
        for (ItemAnnotation itemAnnotation : byPaperIdAndWorkspaceId) {
            itemAnnotation.setPosition(objectMapper.readValue(itemAnnotation.getPositionString(), ItemPosition.class));
        }
        return byPaperIdAndWorkspaceId;
    }
    public ItemAnnotation findByItemId(DeleteItemDTO deleteItemDTO){
        return itemAnnotationMapper.findByItemId(deleteItemDTO);
    }
    public void delete(DeleteItemDTO deleteItemDTO){itemAnnotationMapper.delete(deleteItemDTO);}
}
