package com.example.yeondodemo.service.search;

import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.dto.paper.ExpiredKeyDTO;
import com.example.yeondodemo.dto.paper.PaperAnswerResponseDTO;
import com.example.yeondodemo.dto.paper.PaperResultRequest;
import com.example.yeondodemo.dto.paper.PaperSimpleIdTitleDTO;
import com.example.yeondodemo.dto.paper.item.*;
import com.example.yeondodemo.dto.python.PaperPythonFirstResponseDTO;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import com.example.yeondodemo.dto.python.Token;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.filter.ReadPaper;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.paper.item.BatisItemAnnotationRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.utils.ConnectPythonServer;
import com.example.yeondodemo.utils.PDFReferenceExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service @RequiredArgsConstructor @Slf4j
public class PaperService {
    private final QueryHistoryRepository queryHistoryRepository;
    private final PaperInfoRepository paperInfoRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private final BatisItemAnnotationRepository itemAnnotationRepository;
    private final Cache cacheService;
    private Map<Long, ExpiredKeyDTO> answerIdMap;
    @Value("${python.address}")
    private String pythonapi;
    @Value("${python.key}")
    private String pythonKey;

    public static Map<String,ExpiredPythonAnswerKey> store;
    @ToString
    class ExpiredPythonAnswerKey{
        public PaperAnswerResponseDTO paperAnswerResponseDTO;
        public Long idx;
        public Long expired;
        public Long workspaceId;
        public String paperId;
        public QuestionDTO query;
        ExpiredPythonAnswerKey(Long idx, Long workspaceId, String paperId, QuestionDTO query){
            this.expired = System.currentTimeMillis() + 10 * 60 * 1000; // 현재 시간에 10분을 더한 값
            this.idx = idx;
            this.paperId = paperId;
            this.query = query;
            this.workspaceId = workspaceId;
            this.paperAnswerResponseDTO = new PaperAnswerResponseDTO();
        }
    }
    @PostConstruct
    public void init(){
        store = new ConcurrentHashMap();
        answerIdMap = new ConcurrentHashMap();
    }
    public void timeout(){
        List<Long>  timeoutList = new ArrayList<>();
        for(Long key: answerIdMap.keySet()){
            if (answerIdMap.get(key).getExpired()<System.currentTimeMillis()){
                timeoutList.add(key);
            }
        }
        for(Long key: timeoutList){
            answerIdMap.remove(key);
        }
        List<String>  timeoutList2 = new ArrayList<>();
        for(String key: store.keySet()){
            if (store.get(key).expired<System.currentTimeMillis()){
                timeoutList2.add(key);
            }
        }
        for(String key: timeoutList2){
            store.remove(key);
        }
    }

    @Transactional
    public ResponseEntity deletePaperItem(DeleteItemDTO deleteItemDTO){
        itemAnnotationRepository.delete(deleteItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity putPaperItem(ItemAnnotation paperItem){
        log.info("Update Item: {}", paperItem);
        itemAnnotationRepository.update(paperItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity exportPaper(ExportItemDTO exportItemDTO){
        //todo: 파이썬쪽 로직 짜여지면 통신하는거 추가할것.
        return new ResponseEntity<>(new ExportItemResponse(), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity postPaperItem(ItemAnnotation paperItem){
        log.info("Store Item: {}", paperItem);
        return new ResponseEntity<>(itemAnnotationRepository.save(paperItem), HttpStatus.OK);
    }
    public Long getResultId(Long key) throws IllegalAccessError{
        if (answerIdMap.get(key) != null){
            return answerIdMap.get(key).getRid();
        }else{
            throw new IllegalAccessError("Invalid Access Key");
        }
    }
    @Transactional
    public ResponseEntity storePythonToken(String key,Long rid, Token track){
        if(!key.equals(pythonKey)){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        queryHistoryRepository.updateToken(rid, track);
        return new ResponseEntity(HttpStatus.OK);
    }
    public PythonQuestionDTO getPythonQuestionDTO(String paperid, Long workspaceId, QuestionDTO query){
        List<PaperHistory> paperHistories = queryHistoryRepository.findByUserAndIdOrderQA4Python(workspaceId, paperid);
        return new PythonQuestionDTO(paperid, query, paperHistories);
    }
    @Transactional
    public PaperAnswerResponseDTO getPaperQuestion(String paperid, Long workspaceId, QuestionDTO query){

        PythonQuestionDTO pythonQuestionDTO = getPythonQuestionDTO(paperid, workspaceId, query);
        PythonQuestionResponse answer = ConnectPythonServer.question(pythonQuestionDTO, pythonapi);

        log.info("Python response is.. {}", answer);
        PaperAnswerResponseDTO paperAnswerResponseDTO = new PaperAnswerResponseDTO(answer);
        Long idx = queryHistoryRepository.getLastIdx(workspaceId, paperid);
        if(idx == null) {idx=0L;}

        queryHistoryRepository.save(new QueryHistory(workspaceId, paperid, idx+2, false, paperAnswerResponseDTO));
        queryHistoryRepository.save(new QueryHistory(workspaceId, paperid, idx+1, true, query));

        return paperAnswerResponseDTO;
    }
    @Transactional
    public void setBasis(PythonQuestionResponse pythonQuestionResponse, String key){
        log.info("Set Basis {}", pythonQuestionResponse);
        log.info("key: {}", key);
        log.info("SetBasis, store: {}", store);
        PaperAnswerResponseDTO paperAnswerResponseDTO = new PaperAnswerResponseDTO(pythonQuestionResponse);
        ExpiredPythonAnswerKey expiredPythonAnswerKey = store.get(key);
        expiredPythonAnswerKey.paperAnswerResponseDTO.setPositions(paperAnswerResponseDTO.getPositions());
        queryHistoryRepository.save(new QueryHistory(expiredPythonAnswerKey.workspaceId, expiredPythonAnswerKey.paperId, expiredPythonAnswerKey.idx+2, false, paperAnswerResponseDTO));
        queryHistoryRepository.save(new QueryHistory(expiredPythonAnswerKey.workspaceId, expiredPythonAnswerKey.paperId, expiredPythonAnswerKey.idx+1, true, expiredPythonAnswerKey.query));
    }

    public List<ItemPosition> getBasis(Long workspaceId, String paperid, String key){
        log.info("getBasis, store: {}", store);
        PaperAnswerResponseDTO paperAnswerResponseDTO = store.get(key).paperAnswerResponseDTO;
        log.info("getBasis, paperAnswerResnponseDTO: {}", store.get(key).paperAnswerResponseDTO);
        store.remove(key);
        return paperAnswerResponseDTO.getPositions();
    }

    @Transactional
    public Flux<ServerSentEvent<String>> getPaperQuestionStream(String paperid, Long workspaceId, QuestionDTO query){
        PythonQuestionDTO pythonQuestionDTO = getPythonQuestionDTO(paperid, workspaceId, query);

        List<String> answerList = new ArrayList<>();
        Long lastIdx = queryHistoryRepository.getLastIdx(workspaceId, paperid);
        final long idx = (lastIdx == null) ? 0L : lastIdx;
        log.info("Python RequestBody: {}",pythonQuestionDTO);
        store.put(query.getKey(),new ExpiredPythonAnswerKey(idx, workspaceId, paperid, query));
        Duration timeoutDuration = Duration.ofSeconds(50); // 10초로 설정 (원하는 시간으로 변경 가능)
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 설정은 선택사항
                        .build())
                .baseUrl(pythonapi + "/chat"+"?key="+query.getKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
       return webClient
                .post()
                .uri(pythonapi + "/chat")
                .body(Mono.just(pythonQuestionDTO), PythonQuestionDTO.class)
                .retrieve()
                .bodyToFlux(String.class)
               .timeout(timeoutDuration)
               .map(data -> {
                   int lastIndex = data.lastIndexOf("\n");
                   String trimmedData = lastIndex != -1 ? data.substring(0, lastIndex) : data;
                   answerList.add(trimmedData);
                   return ServerSentEvent.builder(trimmedData).build();
        }).doOnComplete(
                       () -> {
                           String answer = String.join("",answerList);
                           store.get(query.getKey()).paperAnswerResponseDTO.setAnswer(answer);

                       }
               );
    }
    public void updateInfoRepository(PythonPaperInfoDTO pythonPaperInfoDTO, String paperid){
        for(String insight: pythonPaperInfoDTO.getInsights()){
            paperInfoRepository.save(new PaperInfo(paperid, "insight", insight));
        }
        for(String q: pythonPaperInfoDTO.getQuestions()){
            paperInfoRepository.save(new PaperInfo(paperid, "question", q));
        }
        for(String s: pythonPaperInfoDTO.getSubjectRecommends()){
            paperInfoRepository.save(new PaperInfo(paperid, "subjectrecommend", s));
        }
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
    }

    public void updateInfoRepositoryV2(String pythonPaperInfoDTO, String paperid){
        paperInfoRepository.save(new PaperInfo(paperid, "welcomeAnswer", pythonPaperInfoDTO));
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
    }

    public void updateInfoRepositoryV3(PaperPythonFirstResponseDTO pythonPaperInfoDTO, String paperid){
        String summary = pythonPaperInfoDTO.getSummary();
        List<String> questions = pythonPaperInfoDTO.getQuestions();
        paperInfoRepository.save(new PaperInfo(paperid, "summary", summary));
        for (String question : questions) {
            paperInfoRepository.save(new PaperInfo(paperid, "questions", question));
        }
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
    }


    @ReadPaper
    public RetPaperInfoDTO getPaperInfo(String paperid, Long workspaceId) throws JsonProcessingException {
        log.info("getPaperInfo... ");
        cacheService.checkPaperCanCached(paperid);
        RetPaperInfoDTO paperInfoDTO = makeRetPaperInfoDTO(paperid, workspaceId);
        log.info("paper info: {}", paperInfoDTO);
        return paperInfoDTO;
    }

    public RetPaperInfoDTO makeRetPaperInfoDTO(String paperid, long workspaceId) throws JsonProcessingException {
        String pythonPaperInfoDTO = paperInfoRepository.findByPaperIdAndType(paperid, "summary");
        log.info("summary: {}", pythonPaperInfoDTO);
        List<String> questions = paperInfoRepository.findManyByPaperIdAndType(paperid, "questions");
        Paper paper = paperRepository.findById(paperid);
        List<ItemAnnotation> paperItems = itemAnnotationRepository.findByPaperIdAndWorkspaceId(paperid, workspaceId);
        Boolean isLike = likePaperRepository.isLike(workspaceId, paperid);
        List<PaperHistory> paperHistories = queryHistoryRepository.findByUsernameAndPaperIdOrderQA(workspaceId, paperid);
        List<PaperSimpleIdTitleDTO> references = paperRepository.findReferenceById(paperid);
        return new RetPaperInfoDTO(paper, pythonPaperInfoDTO, questions, paperHistories, paperItems, isLike, references);

    }
    @Transactional
    public void resultScore(PaperResultRequest paperResultRequest){
        queryHistoryRepository.updateScore(paperResultRequest.getId(), paperResultRequest.getScore());
    }

}
