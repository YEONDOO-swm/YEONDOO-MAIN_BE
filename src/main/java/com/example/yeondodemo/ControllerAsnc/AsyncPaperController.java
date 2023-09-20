package com.example.yeondodemo.ControllerAsnc;

import com.example.yeondodemo.dto.QuestionDTO;
import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.service.search.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/paper")
public class AsyncPaperController {

    private final PaperService paperService;
    @PostMapping(value = "/{paperid}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody @JwtValidation
    public Flux<ServerSentEvent<String>> paperQuestionStream(@RequestHeader("Gauth") String jwt, @PathVariable("paperid") String paperid, @RequestParam("workspaceId")Long workspaceId, @RequestBody QuestionDTO question,@RequestParam Long key) {
        return paperService.getPaperQuestionStream(paperid, workspaceId, question.getQuestion(), key);
    }
}