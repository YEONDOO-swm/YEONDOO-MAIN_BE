package com.example.yeondodemo.Controller.ControllerAsnc;

import com.example.yeondodemo.dto.QuestionDTO;
import com.example.yeondodemo.service.search.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/paper")
public class AsyncPaperController {

    private final PaperService paperService;
    @PostMapping
    @ResponseBody
    public Flux<String> paperQuestionStream(@RequestHeader("Gauth") String jwt, @RequestParam("workspaceId")Long workspaceId,@RequestParam String paperId,  @RequestBody QuestionDTO question) {
        return paperService.getPaperQuestionStream(paperId, workspaceId, question);
    }
}
