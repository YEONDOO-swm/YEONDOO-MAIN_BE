package com.example.yeondodemo.service.search;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.yeondodemo.dto.*;
import com.example.yeondodemo.dto.history.QueryHistory;
import com.example.yeondodemo.dto.paper.*;
import com.example.yeondodemo.dto.paper.item.*;
import com.example.yeondodemo.dto.python.*;
import com.example.yeondodemo.dto.token.TokenUsageDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.exceptions.PythonServerException;
import com.example.yeondodemo.filter.ReadPaper;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.paper.item.BatisItemAnnotationRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.utils.ConnectPythonServer;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service @RequiredArgsConstructor @Slf4j
public class PaperService {
    private final QueryHistoryRepository queryHistoryRepository;
    private final PaperInfoRepository paperInfoRepository;
    private final PaperBufferRepository paperBufferRepository;
    private final PaperRepository paperRepository;
    private final LikePaperRepository likePaperRepository;
    private final BatisItemAnnotationRepository itemAnnotationRepository;
    private final Cache cacheService;
    private final BatisAuthorRepository authorRepository;
    private final AmazonS3 amazonS3;
    private final JwtTokenProvider jwtTokenProvider;
    private Map<Long, ExpiredKeyDTO> answerIdMap;
    @Value("${python.address}")
    private String pythonapi;
    @Value("${python.key}")
    private String pythonKey;

    private AtomicLong lastPaperId;
    public static Map<String,ExpiredPythonAnswerKey> store;

    public ResponseEntity getToken(String jwt) {
        int token = paperRepository.findLeftQuestionsById(jwtTokenProvider.getUserName(jwt));
        Map<String, Integer> leftQuestions = Map.of("leftQuestions", token);
        return new ResponseEntity(leftQuestions, HttpStatus.OK);
    }

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
    private long loadIdFromFile(String idFilePath) {
        long id = 3L; // 기본값

        File file = new File(idFilePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String idStr = reader.readLine();
                if (idStr != null) {
                    id = Long.parseLong(idStr);
                }
            } catch (IOException e) {
                // 파일 읽기 오류 처리
                e.printStackTrace();
            }
        } else {
            // 파일이 존재하지 않을 때, 새 파일을 생성하고 초기 ID 값 0을 기록
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("1");
            } catch (IOException e) {
                // 파일 생성 또는 초기 ID 값 작성 오류 처리
                e.printStackTrace();
            }
        }


        return id;
    }
    @PostConstruct
    public void init(){
        store = new ConcurrentHashMap();
        answerIdMap = new ConcurrentHashMap();
        lastPaperId = new AtomicLong(loadIdFromFile("./idFile.txt"));
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
    public Flux<String> getPaperQuestionStream(String paperid, Long workspaceId, QuestionDTO query, String jwt){
        String email = jwtTokenProvider.getUserName(jwt);
        PythonQuestionDTO pythonQuestionDTO = getPythonQuestionDTO(paperid, workspaceId, query);
        log.info("python Question DTO: {} ", pythonQuestionDTO);
        List<String> answerList = new ArrayList<>();
        Long lastIdx = queryHistoryRepository.getLastIdx(workspaceId, paperid);
        final long idx = (lastIdx == null) ? 0L : lastIdx;
        log.info("Python RequestBody: {}",pythonQuestionDTO);
        //store.put(query.getKey(),new ExpiredPythonAnswerKey(idx, workspaceId, paperid, query));
        Duration timeoutDuration = Duration.ofSeconds(50);
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 설정은 선택사항
                        .build())
                .baseUrl(pythonapi + "/chat")
                .defaultHeader("Content-Type", "application/json")
                .build();
        return webClient
                .post()
                .uri(pythonapi + "/chat")
                .body(Mono.just(pythonQuestionDTO), PythonQuestionDTO.class)
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(timeoutDuration)
                .flatMap(data -> processData(data))
                .doOnNext(answerList::add)
                .doOnComplete(
                        () -> {
                            String answer = String.join("",answerList);
                            answer = answer.trim();
                            //store.get(query.getKey()).paperAnswerResponseDTO.setAnswer(answer);
                            PaperAnswerResponseDTO paperAnswerResponseDTO = new PaperAnswerResponseDTO();
                            paperAnswerResponseDTO.setAnswer(answer);
                            queryHistoryRepository.save(new QueryHistory(workspaceId, paperid, idx+2, false, paperAnswerResponseDTO));
                            queryHistoryRepository.save(new QueryHistory(workspaceId, paperid, idx+1, true, query));
                            paperRepository.saveUsage(TokenUsageDTO.builder()
                                    .email(email)
                                    .usedDate(LocalDateTime.now())
                                    .build());
                        }
                );
    }

    private Mono<String> processData(String data) {
        return Mono.fromCallable(() -> {
            int lastIndex = data.lastIndexOf("\n");
            String trimmedData = lastIndex != -1 ? data.substring(0, lastIndex) : data;
            if(trimmedData.equals("")){
                trimmedData = "\n\n";
            }
            return trimmedData;
        });
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
    public void updateInfoRepositoryV5(PaperPythonFirstResponseDTO pythonPaperInfoDTO, String paperid){
        paperBufferRepository.update(paperid, new BufferUpdateDTO(true, new Date()));
        paperRepository.saveReferences(pythonPaperInfoDTO.getReferences(), paperid);
    }


    @ReadPaper
    public RetPaperInfoDTO getPaperInfo(String paperid, Long workspaceId) throws JsonProcessingException {
        log.info("getPaperInfo... ");
        PaperPythonFirstResponseDTO paperPythonFirstResponseDTO = cacheService.checkPaperCanCached(paperid);

        if(paperPythonFirstResponseDTO!=null){
            updateInfoRepositoryV5(paperPythonFirstResponseDTO, paperid);
        }
        likePaperRepository.updateDate(workspaceId, paperid);
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
        List<PaperSimpleIdTitleDTO> references = paperRepository.findReferenceById(paperid, workspaceId);
        return new RetPaperInfoDTO(paper, pythonPaperInfoDTO, questions, paperHistories, paperItems, isLike, references);

    }
    @Transactional
    public void resultScore(PaperResultRequest paperResultRequest){
        queryHistoryRepository.updateScore(paperResultRequest.getId(), paperResultRequest.getScore());
    }

    private String idFilePath = "./idFile.txt";

    public Long getNextId() {
        long nextId = lastPaperId.incrementAndGet();
        saveIdToFile(nextId);
        return nextId+2000;
    }

    private void saveIdToFile(long id) {
        File file = new File(idFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Long.toString(id));
        } catch (IOException e) {
            // 파일 쓰기 오류 처리
            e.printStackTrace();
        }
    }


    void storePaper(Long workspaceId, Paper paper){
        String paperId = "9999."+ getNextId().toString();// workspaceId.toString() + "/" + "9999."+ getNextId().toString();
        paper.setPaperId(paperId);
        log.info("try to store.. paperId: {}", paper);
        paper.setUrl("https://yeondoo-upload-pdf.s3.ap-northeast-2.amazonaws.com"+"/"+ paperId + ".pdf");
        paperRepository.save(paper);
        if(paper.getAuthors()!=null && paper.getAuthors().size()!=0){authorRepository.saveAll(paperId, paper.getAuthors());}
        paperBufferRepository.save(new PaperBuffer(paperId));
    }
    void uploadPaper(MultipartFile file, String paperId){
        String bucketName = "yeondoo-upload-pdf";
        String key = paperId + ".pdf";
        try {

            ObjectMetadata metadata = new ObjectMetadata();

            // ContentType을 설정
            metadata.setContentDisposition("inline");
            metadata.setContentType("application/pdf");
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (Exception e) {
            throw new PythonServerException("h");
        }
    }

    void likeOn(String paperId, Long workspaceId){
        likePaperRepository.save(workspaceId, paperId, true);
    }


    @Transactional
    public FileUploadResponse fileUploadAndStore(Long workspaceId,String title, MultipartFile file){
        //paperId규칙: 2017 -> 9999.00001
        //db에 index저장 필요.
        // .pdf
        title = title.replaceAll("(.pdf)*$", "");
        Paper paper = Paper.builder().title(title).userPdf(true).build();
        storePaper(workspaceId, paper);
        uploadPaper(file, paper.getPaperId());
        PaperPythonFirstResponseDTO paperPythonFirstResponseDTO = cacheService.checkPaperCanCached(paper.getPaperId());
        updateInfoRepositoryV5(paperPythonFirstResponseDTO, paper.getPaperId());
        likeOn(paper.getPaperId(), workspaceId);
        return FileUploadResponse.builder().url(paper.getUrl()).paperId(paper.getPaperId()).build();
    }

}
