package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.dbcontroll.AddAuthorDTO;
import com.example.yeondodemo.dto.dbcontroll.AddStudyFieldDTO;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.dto.paper.Version;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.rmi.server.RemoteRef;
import java.util.ArrayList;
import java.util.List;

@Controller @RequiredArgsConstructor
public class DbController {
    private final BatisAuthorRepository batisAuthorRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final PaperRepository paperRepository;
    private final BatisAuthorRepository authorRepository;
    private final PaperBufferRepository paperBufferRepository;
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
        if(paperRepository.findById(data.get(0).getPaperId(), false) == null){
            PaperFullMeta paper = data.get(0);
            if(paper.getComments()!= null && paper.getComments().length()>=450){
                paper.setComments(paper.getComments().substring(0,400));
            }
            if(paper.getJournalRef()!= null && paper.getJournalRef().length()>=300){
                paper.setJournalRef(paper.getJournalRef().substring(0,298));
            }
            if(paper.getSubmitter()!= null && paper.getSubmitter().length()>=100){
                paper.setSubmitter(paper.getSubmitter().substring(0,98));
            }
            if(paper.getDoi() != null && paper.getDoi().length()>=100){
                paper.setDoi(paper.getDoi().substring(0,98));
            }
            List<Version> versions = paper.getVersions();
            if (versions != null && !versions.isEmpty()) {
                String lastVersion = versions.get(versions.size() - 1).getVersion();
                paper.setVersion(lastVersion);
            }
            String input = "";
            try{
                paperRepository.saveF(paper);
                paperBufferRepository.save(new PaperBuffer(paper.getPaperId(), false));
                input = paper.getAuthors();

                if (input != null && !input.isEmpty()) {
                    String[] nameArray = input.split(", ");
                    for (String name : nameArray) {
                        if(name.length()>50){
                            continue;
                        }
                        authorRepository.save(paper.getPaperId(), name);
                    }
                }

            }catch (Exception e){
                System.out.println("sizetitle : " + paper.getTitle().length());
                System.out.println("sizesize : " + paper.getComments().length());
                System.out.println("sizej : " + paper.getJournalRef().length());
                System.out.println("sizesizesub : " + paper.getSubmitter().length());
                System.out.println("sizesdoi : " + paper.getDoi().length());
                System.out.println("sizescate : " + paper.getCategories().length());
                System.out.println("input = " + input);
                Thread.sleep(100000000);

            }
        }
        Thread.sleep(1);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/uploadAuthor")
    public ResponseEntity addAuthors(@RequestBody List<PaperFullMeta> data) throws InterruptedException {
        if(paperRepository.findById(data.get(0).getPaperId(), false) != null){
            PaperFullMeta paper = data.get(0);
            List<String> names = new ArrayList<>();
            String input = paper.getAuthors();
            if (input != null && !input.isEmpty()) {
                String[] nameArray = input.split(", ");
                for (String name : nameArray) {
                    names.add(name);
                }
            }
            for (String name : names) {
                authorRepository.save(paper.getPaperId(), name);

            }

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
