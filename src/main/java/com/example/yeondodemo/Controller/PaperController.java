package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.QuestionDTO;
import com.example.yeondodemo.service.search.PaperService;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.validation.LoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.yeondodemo.validation.IntegrationValidator.inValidPaperUserRequest;

@Controller @RequiredArgsConstructor @RequestMapping("/api/paper")
public class PaperController {
    private final PaperService paperService;
    @GetMapping("/{paperid}")
    public ResponseEntity paperInfo(@PathVariable String paperid, @RequestParam String username){
        ResponseEntity<Object> BAD_REQUEST = inValidPaperUserRequest(paperid, username);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return new ResponseEntity<>(paperService.getPaperInfo(paperid, username), HttpStatus.OK);
    }
    @PostMapping("/{paperid}")
    public ResponseEntity paperQuestion(@PathVariable String paperid, @RequestParam String username, @Validated @RequestBody QuestionDTO question, BindingResult bindingResult){
        ResponseEntity<Object> BAD_REQUEST = inValidPaperUserRequest(paperid, username, bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return new ResponseEntity<>(paperService.getPaperQuestion(paperid, username, question.getQuestion()), HttpStatus.OK);
    }

}
