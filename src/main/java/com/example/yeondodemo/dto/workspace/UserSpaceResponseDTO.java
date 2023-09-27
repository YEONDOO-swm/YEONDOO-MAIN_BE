package com.example.yeondodemo.dto.workspace;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class UserSpaceResponseDTO {
    private List<UserSpaceDTO> workspaces;
    public UserSpaceResponseDTO(List<UserSpaceDTO> workspaces){
        this.workspaces = workspaces;
    }

}
