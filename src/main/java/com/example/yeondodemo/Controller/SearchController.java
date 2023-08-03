package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.LikeOnOffDTO;
import com.example.yeondodemo.dto.PythonResultDTO;
import com.example.yeondodemo.dto.SearchResultDTO;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.utils.ConnectPythonServer;
import com.example.yeondodemo.validation.UserValidator;
import com.example.yeondodemo.validation.SearchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.yeondodemo.validation.IntegrationValidator.inValidPaperUserRequest;

@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/api")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/homesearch")
    public ResponseEntity search(@RequestParam String query, @RequestParam String username, @RequestParam Integer searchType){
        //SearchType: 1: 논문 검색, 2: 개념 설명
        if(SearchValidator.isNotValidateSearchQuery(query)){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        SearchResultDTO searchResultDTO = searchService.search(query, username, searchType);
        log.info("python search: {}",searchResultDTO.toString());
        return new ResponseEntity<>(searchResultDTO, HttpStatus.OK);
    }
    @PostMapping("/paperlikeonoff")
    public ResponseEntity likeOnOff(@Validated @RequestBody LikeOnOffDTO likeOnOffDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            log.info("errors: {}", bindingResult);
            switch(Objects.requireNonNull(bindingResult.getFieldError().getCode())){
                //@1Login기능 제대로 구현시 UnAuthorized추가할것.
                case "AssertFalse":
                    status = HttpStatus.UNAUTHORIZED;
                    break;
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
    public ResponseEntity paperContainer(@RequestParam String username){
        if(UserValidator.isNotValidName(username)){return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        return new ResponseEntity<>(searchService.getPaperContainer(username), HttpStatus.OK);
    }

}
