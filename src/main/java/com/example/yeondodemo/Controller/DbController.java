package com.example.yeondodemo.Controller;

import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.dbcontroll.AddAuthorDTO;
import com.example.yeondodemo.dto.dbcontroll.AddStudyFieldDTO;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.dto.paper.Version;
import com.example.yeondodemo.dto.python.Token;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.service.WorkspaceService;
import com.example.yeondodemo.service.search.PaperService;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opensearch.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.server.RemoteRef;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller @RequiredArgsConstructor @Slf4j
public class DbController {
    private final BatisAuthorRepository batisAuthorRepository;
    private final StudyFieldRepository studyFieldRepository;
    private final PaperRepository paperRepository;
    private final BatisAuthorRepository authorRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperService paperService;
    @Autowired
    private RefreshRedisRepository repository;


    private final WorkspaceService workspaceService;

    @PostMapping("api/python/token")
    public ResponseEntity storePythonToken(@RequestParam String key,@RequestParam Long historyId, @RequestBody Token track){
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
