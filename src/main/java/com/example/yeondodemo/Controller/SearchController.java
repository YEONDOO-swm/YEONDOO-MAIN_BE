package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.paper.LikeOnOffDTO;
import com.example.yeondodemo.dto.SearchResultDTO;
import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.filter.JwtValidation;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.validation.PaperValidator;
import com.example.yeondodemo.validation.SearchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/api")
public class SearchController {
    private final SearchService searchService;
    @GetMapping("/homesearch")
    public ResponseEntity search(@RequestHeader("Gauth") String jwt,@RequestParam Long workspaceId,@RequestParam String query){
        //SearchType: 1: 논문 검색, 2: 개념 설명
        int searchType=1;
        if(SearchValidator.isNotValidateSearchQuery(query, searchType)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        SearchResultDTO searchResultDTO = searchService.search(query, workspaceId, searchType);
        log.info("python search: {}",searchResultDTO.toString());
        return new ResponseEntity<>(searchResultDTO, HttpStatus.OK);
    }

    @PostMapping("home/result/score")
    public ResponseEntity resultScore(@RequestHeader("Gauth") String jwt,@RequestParam Long workspaceId, @Validated @RequestBody PaperResultRequest paperResultRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()|| PaperValidator.isNotValidHomeResultId(workspaceId, paperResultRequest)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        searchService.resultScore(paperResultRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/paperlikeonoff") @JwtValidation
    public ResponseEntity likeOnOff(@RequestHeader("Gauth") String jwt,@Validated @RequestBody LikeOnOffDTO likeOnOffDTO, BindingResult bindingResult){
        log.info("likepaperDTO: {}", likeOnOffDTO);
        if(bindingResult.hasErrors()){
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            log.info("errors: {}", bindingResult);
            switch(Objects.requireNonNull(bindingResult.getFieldError().getCode())){
                //@1Login기능 제대로 구현시 UnAuthorized추가할것.
                case "AssertTrue":
                    status = HttpStatus.BAD_REQUEST;
                    break;
            }
            return new ResponseEntity<>(status);
        }
        searchService.likeonoff(likeOnOffDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("/container")
    public ResponseEntity paperContainer(@RequestHeader("Gauth") String jwt,@RequestParam("workspaceId") Long workspaceId){
        return new ResponseEntity<>(searchService.getPaperContainer(workspaceId), HttpStatus.OK);
    }

}
