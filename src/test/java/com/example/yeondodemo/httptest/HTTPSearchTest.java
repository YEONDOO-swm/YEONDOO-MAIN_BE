package com.example.yeondodemo.httptest;


import com.example.yeondodemo.Controller.SearchController;
import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.Version;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.history.BatisSearchHistoryRepository;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.paper.*;
import com.example.yeondodemo.repository.paper.batis.BatisPaperBufferRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperInfoRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperRepository;
import com.example.yeondodemo.repository.paper.batis.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.studyfield.BatisStudyFieldRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.*;
import com.example.yeondodemo.dto.LikeOnOffDTO;
import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.repository.user.batis.BatisLikePaperRepository;
import com.example.yeondodemo.repository.user.batis.BatisRealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisUserRepository;
import com.example.yeondodemo.service.login.TokenType;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.Updater;
import com.example.yeondodemo.validation.WorkspaceValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.utils.installFastApi;
import static util.utils.isFastApiInstalled;

@EnableAspectJAutoProxy
@WithMockUser
@WebMvcTest(SearchController.class) @AutoConfigureWebMvc @AutoConfigureMybatis
@Import({BatisRealUserRepository.class, JwtTokenProvider.class,BatisPaperRepository.class, BatisSearchHistoryRepository.class, Updater.class, BatisAuthorRepository.class, BatisPaperBufferRepository.class, BatisPaperInfoRepository.class, BatisQueryHistoryRepository.class, SearchService.class,  BatisLikePaperRepository.class, BatisUserRepository.class, BatisStudyFieldRepository.class, BatisLikePaperRepository.class})
public class HTTPSearchTest {
    @InjectMocks
    private SearchService searchService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudyFieldRepository studyFieldRepository;
    @Autowired
    PaperRepository paperRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    LikePaperRepository likePaperRepository;
    @Autowired
    QueryHistoryRepository queryHistoryRepository;
    @Autowired
    PaperInfoRepository paperInfoRepository;
    @Autowired
    PaperBufferRepository paperBufferRepository;
    @Autowired
    BatisAuthorRepository authorRepository;
    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    RealUserRepository realUserRepository;
    @Autowired
    JwtTokenProvider provider;
    String jwt;
    TransactionStatus status;

    private static Process process;
    @BeforeAll
    public static void pythonsOn() throws IOException, InterruptedException {
        // FastAPI 서버를 실행시키는 명령어
        if (!isFastApiInstalled()) {
            // FastAPI 설치
            installFastApi();
        }
        String command = "uvicorn src.test.python.pythonmock:app --reload";
        process= Runtime.getRuntime().exec(command);
        System.out.println("ffdfad: "+System.getProperty("user.dir"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @AfterAll
    public static void stopFastApi() {
        // FastAPI 서버를 종료시키는 로직 추가
        if (process != null) {
            process.destroy();
        }
    }
    @BeforeEach
    public void beforeEach() {
        String email = "test@test.com";
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        realUserRepository.save(email);
        Workspace user1 = new Workspace(0l, "testtest");
        Workspace user2 = new Workspace(1L, "testtest");
        realUserRepository.saveWorkspace(email, user1);
        realUserRepository.saveWorkspace(email, user2);
        jwt = provider.createJwt(email, TokenType.ACCESS);
        studyFieldRepository.save("1234");
        studyFieldRepository.save("12345");
        studyFieldRepository.save("12346");
        studyFieldRepository.save("12347");
        WorkspaceValidator.login.put(jwt, new HashSet<Long>());
        WorkspaceValidator.login.get(jwt).add(0L);
        WorkspaceValidator.login.get(jwt).add(1L);
    }
    @AfterEach
    public void afterEach(){
        transactionManager.rollback(status);
    }
    @Test
    public void searchSuccess() throws Exception {
        TestPython testPython1 = new TestPython();
        TestPython testPython2 = new TestPython();
        testPython1.setAuthors(Arrays.asList("Changjian Li"));
        testPython1.setYear(2020);
        testPython1.setPaperId("1706.03762");
        testPython1.setUrl("http~~~");
        testPython1.setSummary("~~~");

        testPython2.setYear(2020);
        testPython2.setAuthors(Arrays.asList("Changjian Li", "hihi"));
        testPython2.setPaperId("2204.13154");
        testPython2.setSummary("~~~");
        testPython2.setUrl("http~~~");
        List<TestPython> test = new ArrayList<>(Arrays.asList(testPython1,testPython2));
        paperRepository.findById("1706.03762");
        paperRepository.findById("2204.13154");
        likePaperRepository.save(0L, "1706.03762", true);
        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", "hi")
                        .queryParam("searchType", "2")
        ).andExpect(
                status().isOk()
        )
                .andExpect(jsonPath("$.answer").value("reinforcement learning is a learning paradigm for solving sequential decision-making problems. It works by learning from trial-and-error interactions with the environment over its lifetime, and can be enhanced by incorporating causal relationships into the learning process."))
                .andExpect(jsonPath("$.papers[0].likes").value(0))
                .andExpect(jsonPath("$.papers[0].isLike").value(true))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is All you"))
                .andExpect(jsonPath("$.papers[0].cites").isNumber())
                .andExpect(jsonPath("$.papers[1].likes").value(0))
                .andExpect(jsonPath("$.papers[1].isLike").value(false))
                .andExpect(jsonPath("$.papers[1].title").value("Insights into Lifelong Reinforcement Learning Systems"))
                .andExpect(jsonPath("$.papers[1].cites").isNumber());



        mockMvc.perform(
                        get("http://localhost:8080/api/homesearch")
                                .header("Gauth", jwt)
                                .queryParam("workspaceId", "0")
                                .queryParam("query", "Attention is all you need")
                                .queryParam("searchType", "1")
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.answer").value("Attention is all you need"))
                .andExpect(jsonPath("$.papers[0].likes").value(0))
                .andExpect(jsonPath("$.papers[0].isLike").value(true))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is All you"))
                .andExpect(jsonPath("$.papers[0].cites").isNumber());
    }
    @Test
    public void searchFailWhenNull() throws Exception{
        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", "")
                        .queryParam("searchType", "1")

        ).andExpect(
                status().isBadRequest()
        );
    }
    @Test
    public void searchFailWhenOver300() throws Exception{
        List<String> a = new ArrayList<>();
        for(int i = 0; i < 301; i++){
            a.add("1");
        }
        String s = a.toString();

        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", s)
                        .queryParam("searchType", "1")
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    public void likeOnTestSuccess() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setWorkspaceId(0L);
        likeOnOffDTO.setPaperId("2010.01369");
        likeOnOffDTO.setOn(true);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        Assertions.assertThat(likePaperRepository.findByUser(0L)).contains("2010.01369");
    }
    @Test
    public void likeOffTestSuccess() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setWorkspaceId(0L);
        likeOnOffDTO.setPaperId("2307.00865");
        likeOnOffDTO.setOn(false);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        Assertions.assertThat(likePaperRepository.findByUser(0L)).doesNotContain("2307.00865");
    }

    @Test
    public void likeTestFailBadRequestTryToOn() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setWorkspaceId(0L);
        likeOnOffDTO.setPaperId("2307.00865");
        likeOnOffDTO.setOn(true);
        paperRepository.findById("2307.00865");
        likePaperRepository.save(0L, "2307.00865", true);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }
    @Test
    public void likeTestFailBadRequestTryToOff() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setWorkspaceId(0L);
        likeOnOffDTO.setPaperId("2010.01369");
        likeOnOffDTO.setOn (false);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }
    public void ContainerSuccess() throws Exception {
        //when null
        mockMvc.perform(
                get("http://localhost:8080/api/container")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId","2")
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.papers").isEmpty()
        );
        //when notnull
        mockMvc.perform(
                get("http://localhost:8080/api/container")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId","1")
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$").isArray()
        ).andExpect(
                jsonPath("$[0].title").value("Attention is all you need")
        ).andExpect(jsonPath("$.papers[0].paperId").value("1706.03762")
        ).andExpect(jsonPath("$.papers[0].authors[0]").value("Ashish Vaswani"))
        .andExpect(jsonPath("$.papers[0].authors[1]").value("Noam Shazeer"))
        .andExpect(jsonPath("$.papers[0].authors[2]").value("Niki Parmar"))
        .andExpect(jsonPath("$.papers[0].year").value(2017)
        ).andExpect(jsonPath("$.papers[0].conference").value("2017 - proceedings.neurips.cc")
        ).andExpect(jsonPath("$.papers[0].cites").value(81192)
        ).andExpect(jsonPath("$.papers[0].url").value("https://arxiv.org/abs/1706.03762")
        ).andExpect(jsonPath("$.papers[1].paperId").value("2307.00865"))

        ;

    }
    public void parsing(){
        Gson gson = new Gson();
        int n = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/iseog-u/Desktop/ComputerScience/study/yeondoDemo/gradle/arxiv-metadata-oai-snapshot.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                n++;
                if(n<1000000){
                    continue;
                }
                if(n%1000 == 0){
                    System.out.println("nnnnnn" + n);
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
                } catch (JsonSyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
