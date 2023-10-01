package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.workspace.WorkspacePutDTO;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.filter.WorkspaceAdd;
import com.example.yeondodemo.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @GetMapping("/workspaceEnter")
    public ResponseEntity getWorkspaceHome(@RequestHeader("Gauth") String jwt, @RequestParam Long workspaceId){
       return new ResponseEntity(workspaceService.getWorkspaceHome(workspaceId), HttpStatus.OK);
    }

    @GetMapping("/workspaces")
    @JwtValidation
    public ResponseEntity getWorkspaces(@RequestHeader("Gauth") String jwt) {
        return new ResponseEntity(workspaceService.getUserSpaces(jwt), HttpStatus.OK);
    }
    @GetMapping("/workspaceCRUD") @WorkspaceAdd
    public ResponseEntity getWorkspaceMeta(@RequestHeader("Gauth") String jwt, @RequestParam  Long workspaceId){
        return new ResponseEntity(workspaceService.getWorkspaceMeta(workspaceId), HttpStatus.OK);
    }


    @PostMapping("/workspaceCRUD") @JwtValidation
    public ResponseEntity postWorkspace(@RequestHeader("Gauth") String jwt, @RequestBody Workspace workspace){
        return new ResponseEntity(workspaceService.setWorkspace(jwt, workspace), HttpStatus.OK);
    }

    @PutMapping("/workspaceCRUD")
    public ResponseEntity putWorkspace(@RequestHeader("Gauth") String jwt,@RequestParam Long workspaceId, @RequestBody WorkspacePutDTO workspace){
        workspaceService.updateWorkspace(workspace);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("/workspaceCRUD")
    public ResponseEntity delWorkspace(@RequestHeader("Gauth") String jwt,@RequestParam Long workspaceId){
        workspaceService.updateWorkspaceValidity(jwt, workspaceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
