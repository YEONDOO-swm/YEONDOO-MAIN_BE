package com.example.yeondodemo.repository.paper.item.mapper;

import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.dto.paper.item.DeleteItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemAnnotationMapper {
    void save(ItemAnnotation itemAnnotation);
    void update(@Param("updateParam") ItemAnnotation itemAnnotation);
    List<ItemAnnotation> findByPaperIdAndWorkspaceId(String paperId, Long workspaceId);
    ItemAnnotation findByItemId(DeleteItemDTO deleteItemDTO);
    void delete(DeleteItemDTO deleteItemDTO);

}
