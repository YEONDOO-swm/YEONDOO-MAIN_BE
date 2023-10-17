package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.dbcontroll.AddAuthorDTO;
import com.example.yeondodemo.dto.dbcontroll.AddStudyFieldDTO;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.Version;
import com.example.yeondodemo.dto.python.Token;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.service.WorkspaceService;
import com.example.yeondodemo.service.search.PaperService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller @RequiredArgsConstructor @Slf4j
public class YeondooDbController {
    private final BatisAuthorRepository batisAuthorRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final PaperRepository paperRepository;
    private final BatisAuthorRepository authorRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperService paperService;
    @Autowired
    private RefreshRedisRepository repository;


    private final WorkspaceService workspaceService;
    @GetMapping("db/getNullPaper")
    public ResponseEntity getNullPaper(){
        return new ResponseEntity(paperRepository.findAllNullPaperId(), HttpStatus.OK);
    }

    @PostMapping("api/python/token")
    public ResponseEntity storePythonToken(@RequestParam String key,@RequestParam long historyId, @RequestBody Token track){
        return paperService.storePythonToken(key, historyId, track);
    }

    @GetMapping("api/workspaceEnter")
    public ResponseEntity getWorkspaceHome(@RequestHeader("Gauth") String jwt, @RequestParam Long workspaceId){
        return new ResponseEntity(workspaceService.getWorkspaceHome(workspaceId), HttpStatus.OK);
    }

    @GetMapping("test/redis")
    public ResponseEntity testRedis(){
        String key = UUID.randomUUID().toString();
        RefreshEntity refreshEntity = new RefreshEntity(key, "abcd1234");
        repository.save(refreshEntity);
        System.out.println(repository.findById(key));
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/getStreamFromFastAPI",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<ServerSentEvent<String>>  streamData() {
        // Replace this with the actual FastAPI endpoint URL where data is streamed
        String fastApiStreamingEndpoint = "http://localhost:8000/test/stream";
        return WebClient.create()
                .get()
                .uri(fastApiStreamingEndpoint)
                .retrieve()
                .bodyToFlux(String.class)
                .map(data -> ServerSentEvent.builder(data).build());
    }
    private final RestTemplate restTemplate =  new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    @GetMapping(value = "/getStreamFromFastAPI2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> getStream() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        StreamingResponseBody responseBody = outputStream -> {
            try {
                // Send a GET request to the FastAPI endpoint
                String fastApiUrl = "http://localhost:port/test/stream"; // Replace with your FastAPI server URL
                String response = restTemplate.getForObject(fastApiUrl, String.class);

                // Write the FastAPI response to the output stream
                outputStream.write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }

    @GetMapping("/addLocal")
    public ResponseEntity addPapers() throws InterruptedException {
        Gson gson = new Gson();
        int n = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                n++;
                if(n<3000000){
                    continue;
                }
                try {
                    PaperFullMeta paper= gson.fromJson(line, PaperFullMeta.class);

                    if(paperRepository.findById(paper.getPaperId(), false) == null){
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
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

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
            if(paper.getTitle()!= null && paper.getTitle().length()>=300){
                paper.setTitle(paper.getTitle().substring(0,300));
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
                System.out.println(paper);
                Thread.sleep(100000000);

            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updatePapers(@RequestBody List<PaperFullMeta> data) throws InterruptedException {

        List<Version> versions = data.get(0).getVersions();
        if (versions != null && !versions.isEmpty()) {
            String lastVersion = versions.get(versions.size() - 1).getVersion();
            data.get(0).setVersion(lastVersion);
        }
        Paper paper = paperRepository.findById(data.get(0).getPaperId());

        convertToPaper(data.get(0), paper);
        paperRepository.update(data.get(0).getPaperId(), paper);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private static int parseYear(String paperId) {
        // PaperId의 형식에 따라 Year 추출
        if (paperId.matches("\\d{6}\\.\\d{5}")) {
            int year = Integer.parseInt(paperId.substring(0, 2));
            if (year >= 97) {
                return 1900 + year;
            } else {
                return 2000 + year;
            }
        } else if (paperId.matches("\\d{4}\\.\\d{4}")) {
            int year = Integer.parseInt(paperId.substring(0, 2));
            if (year >= 97) {
                return 1900 + year;
            } else {
                return 2000 + year;
            }
        } else if (paperId.matches(".*/\\d{6}\\d+")) {
            int year = Integer.parseInt(paperId.substring(paperId.indexOf("/") + 1, paperId.indexOf("/") + 3));
            if (year >= 97) {
                return 1900 + year;
            } else {
                return 2000 + year;
            }
        } else {
            return 0; // 알 수 없는 경우 0으로 설정
        }
    }

    private static String generateUrl(String paperId, String version) {
        // Url 생성
        return "https://arxiv.org/abs/" + paperId + "/" + version;
    }
    public static Paper convertToPaper(PaperFullMeta paperFullMeta,Paper paper) {
        paper.setPaperId(paperFullMeta.getPaperId());
        paper.setTitle(paperFullMeta.getTitle());
        paper.setUrl(generateUrl(paperFullMeta.getPaperId(), paperFullMeta.getVersion()));
        paper.setAbs(paperFullMeta.getSummary()); // 또는 원하는 필드로 설정
        paper.setLikes(0); // 기본값 설정
        paper.setYear(parseYear(paperFullMeta.getPaperId()));
        paper.setComments(paperFullMeta.getComments());
        paper.setJournalRef(paperFullMeta.getJournalRef());
        paper.setDoi(paperFullMeta.getDoi());
        paper.setReportNo(paperFullMeta.getReportNo());
        paper.setCategories(paperFullMeta.getCategories());
        paper.setLicense(paperFullMeta.getLicense());
        paper.setVersion(paperFullMeta.getVersion());
        return paper;
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
