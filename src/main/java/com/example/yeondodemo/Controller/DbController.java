package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.dbcontroll.AddAuthorDTO;
import com.example.yeondodemo.dto.dbcontroll.AddStudyFieldDTO;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.rmi.server.RemoteRef;
import java.util.List;

@Controller @RequiredArgsConstructor
public class DbController {
    private final BatisAuthorRepository batisAuthorRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final PaperRepository paperRepository;
    @PostMapping("/addauthor")
    public ResponseEntity addAuthor(@Validated @RequestBody AddAuthorDTO addAuthorDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){return new ResponseEntity(HttpStatus.BAD_REQUEST);}
        batisAuthorRepository.saveAll(addAuthorDTO.getPaperId(), addAuthorDTO.getAuthors());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/addStudyField")
    public ResponseEntity addStudyField(@RequestBody AddStudyFieldDTO studyFieldDTO){
        studyFieldRepository.saveAll(studyFieldDTO.getStudyFields());
        return new ResponseEntity<>(studyFieldDTO.getStudyFields(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity addPapers(@RequestBody List<PaperFullMeta> data) throws InterruptedException {
        System.out.println("data.get(0).getId() = " + data.get(0).getId());
        Paper paper = new Paper(data.get(0));
        //paperRepository.save();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
