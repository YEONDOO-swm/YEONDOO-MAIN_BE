package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.history.HistorySearchDTO;
import com.example.yeondodemo.service.search.HistoryService;
import com.example.yeondodemo.validation.PaperValidator;
import com.example.yeondodemo.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller @RequestMapping("/api/history") @RequiredArgsConstructor @Slf4j
public class HistoryController {
    private final HistoryService historyService;
    @GetMapping("/search")
    public ResponseEntity historySearch(@RequestHeader("gauth") String jwt, @RequestParam String username){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        return new ResponseEntity<>(historyService.historySearch(username), HttpStatus.OK);
    }
    @GetMapping("/search/result/{resultId}")
    public ResponseEntity historySearchResult(@RequestHeader("gauth") String jwt,@RequestParam String username, @PathVariable Long resultId){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        if(PaperValidator.isNotValidRid(username, resultId)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(historyService.historySearchResult(username, resultId), HttpStatus.OK);
    }
    @GetMapping("/trash")
    public ResponseEntity historySearchTrash(@RequestHeader("gauth") String jwt,@RequestParam String username){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        return new ResponseEntity<>(historyService.historySearchTrash(username), HttpStatus.OK);

    }
    @PostMapping("/trash")
    public ResponseEntity historySearchTrash(@RequestHeader("gauth") String jwt,@RequestParam String username, @RequestBody List<String> papers){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        if(PaperValidator.isNotValidRestorePapers(username, papers)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        historyService.historySearchRestoreTrash(username, papers);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/search/paper")
    public ResponseEntity historyPaper(@RequestHeader("gauth") String jwt,@RequestParam String username){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        return new ResponseEntity(historyService.historyPaper(username), HttpStatus.OK);
    }

}
