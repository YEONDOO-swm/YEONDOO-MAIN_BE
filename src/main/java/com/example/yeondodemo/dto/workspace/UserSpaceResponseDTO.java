package com.example.yeondodemo.dto.workspace;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class UserSpaceResponseDTO {
    private List<UserSpaceDTO> workspaces;
    public UserSpaceResponseDTO(List<UserSpaceDTO> workspaces){
        this.workspaces = workspaces;
    }

}
