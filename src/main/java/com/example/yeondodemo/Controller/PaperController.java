package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.service.search.PaperService;
import com.example.yeondodemo.validation.PaperValidator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.yeondodemo.validation.IntegrationValidator.inValidPaperUserRequest;

@RestController @Slf4j
@RequiredArgsConstructor @RequestMapping("/api/paper")
public class PaperController {
    private final PaperService paperService;
    @GetMapping("/{paperid}")
    public ResponseEntity paperInfo(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId")Long workspaceId, @PathVariable String paperid){
        ResponseEntity<Object> BAD_REQUEST = inValidPaperUserRequest(paperid, workspaceId);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return new ResponseEntity<>(paperService.getPaperInfo(paperid, workspaceId), HttpStatus.OK);
    }
    @GetMapping("/resultId")
    public ResponseEntity getAnswerResultId(@RequestHeader("Gaut") String jwt, @RequestParam Long key){
        try{
            return new ResponseEntity<>(paperService.getResultId(key), HttpStatus.OK);
        }catch (IllegalAccessError e){
            log.info("Invalid access: key: {}", key);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


/*        String fastApiStreamingEndpoint = "http://localhost:8000/test/stream";
        return WebClient.create()
                .get()
                .uri(fastApiStreamingEndpoint)
                .retrieve()
                .bodyToFlux(String.class)x
                .map(data -> ServerSentEvent.builder(data).build());*/


    //@PostMapping("/{paperid}")
//    public ResponseEntity paperQuestion(@RequestHeader("Gauth") String jwt,@PathVariable String paperid, @RequestParam Long workspaceId, @Validated @RequestBody QuestionDTO question, BindingResult bindingResult){
//        ResponseEntity<Object> BAD_REQUEST = inValidPaperUserRequest(paperid, username, bindingResult);
//        if (BAD_REQUEST != null) return BAD_REQUEST;
//        return new ResponseEntity<>(paperService.getPaperQuestion(paperid, username, question.getQuestion()), HttpStatus.OK);
//    }

    @PostMapping("/result/score")
    public ResponseEntity resultScore(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId, @Validated @RequestBody PaperResultRequest paperResultRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()|| PaperValidator.isNotValidResultId(workspaceId, paperResultRequest)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        paperService.resultScore(paperResultRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
