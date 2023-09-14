package com.example.yeondodemo.Controller;

import com.example.yeondodemo.service.search.HistoryService;
import com.example.yeondodemo.validation.PaperValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller @RequestMapping("/api/history") @RequiredArgsConstructor @Slf4j
public class HistoryController {
    private final HistoryService historyService;
    @GetMapping("/search")
    public ResponseEntity historySearch(@RequestHeader("Gauth") String jwt, @RequestParam("workspaceId") Long workspaceId){
        return new ResponseEntity<>(historyService.historySearch(workspaceId), HttpStatus.OK);
    }
    @GetMapping("/search/result/{resultId}")
    public ResponseEntity historySearchResult(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId, @PathVariable Long resultId){
        if(PaperValidator.isNotValidRid(workspaceId, resultId)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(historyService.historySearchResult(workspaceId, resultId), HttpStatus.OK);
    }
    @GetMapping("/trash")
    public ResponseEntity historySearchTrash(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId){
        return new ResponseEntity<>(historyService.historySearchTrash(workspaceId), HttpStatus.OK);

    }
    @PostMapping("/trash")
    public ResponseEntity historySearchTrash(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId, @RequestBody List<String> papers){
        if(PaperValidator.isNotValidRestorePapers(workspaceId, papers)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        historyService.historySearchRestoreTrash(workspaceId, papers);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/search/paper")
    public ResponseEntity historyPaper(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId){
        return new ResponseEntity(historyService.historyPaper(workspaceId), HttpStatus.OK);
    }

}
