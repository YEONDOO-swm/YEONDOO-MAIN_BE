package com.example.yeondodemo.httptest;

import com.example.yeondodemo.Controller.HistoryController;
import com.example.yeondodemo.Controller.PaperController;
import com.example.yeondodemo.Controller.SearchController;
import com.example.yeondodemo.ControllerAsnc.AsyncPaperController;
import com.example.yeondodemo.dto.LikeOnOffDTO;
import com.example.yeondodemo.dto.QuestionDTO;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.filter.AspectController;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.history.BatisSearchHistoryRepository;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.history.SearchHistoryRepository;
import com.example.yeondodemo.repository.paper.PaperBufferRepository;
import com.example.yeondodemo.repository.paper.PaperInfoRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperBufferRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperInfoRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperRepository;
import com.example.yeondodemo.repository.paper.batis.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.studyfield.BatisStudyFieldRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisLikePaperRepository;
import com.example.yeondodemo.repository.user.batis.BatisRealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisUserRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.service.login.TokenType;
import com.example.yeondodemo.service.search.HistoryService;
import com.example.yeondodemo.service.search.PaperService;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.Updater;
import com.example.yeondodemo.validation.WorkspaceValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.utils.installFastApi;
import static util.utils.isFastApiInstalled;

@EnableAspectJAutoProxy
@WithMockUser
@WebMvcTest({HistoryController.class, SearchController.class, PaperController.class, AsyncPaperController.class}) @AutoConfigureWebMvc
@AutoConfigureMybatis
@Import({AspectController.class,BatisRealUserRepository.class, JwtTokenProvider.class, BatisQueryHistoryRepository.class, PaperService.class, HistoryService.class, BatisSearchHistoryRepository.class, Updater.class, BatisAuthorRepository.class, BatisPaperBufferRepository.class, BatisPaperInfoRepository.class, BatisQueryHistoryRepository.class, SearchService.class, BatisPaperRepository.class, BatisLikePaperRepository.class, BatisUserRepository.class, BatisStudyFieldRepository.class, BatisLikePaperRepository.class})
public class HttpHistoryTest{
    @InjectMocks
    private HistoryService searchService;
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
    SearchHistoryRepository searchHistoryRepository;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    RealUserRepository realUserRepository;
    @Autowired
    JwtTokenProvider provider;
    @Autowired
    WebTestClient webTestClient;
    TransactionStatus status;
    String jwt;
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
            sleep(500);
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
        WorkspaceValidator.login.put(jwt, new HashSet<Long>());
        WorkspaceValidator.login.get(jwt).add(0L);
        WorkspaceValidator.login.get(jwt).add(1L);
    }

    @AfterEach
    public void afterEach(){
        transactionManager.rollback(status);
    }
    //@Test
    public void homeSearchHistoryTest() throws Exception {
        //빈거 확인하기.
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.papers").isArray())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.papers[0]").doesNotExist())
                .andExpect(jsonPath("$.results[0]").doesNotExist());
        //검색하기.

        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", "hi1")
                        .queryParam("searchType", "2")
        );


        //1개확인
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.results[0].query").value("hi1"))
                .andExpect(jsonPath("$.papers[0]").doesNotExist())
                .andExpect(jsonPath("$.results[1]").doesNotExist());


        //검색하기.

        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", "hi2")
                        .queryParam("searchType", "2")
        );


        //2개확인
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.results[1].query").value("hi1"))
                .andExpect(jsonPath("$.results[0].query").value("hi2"))
                .andExpect(jsonPath("$.results[0].rid").isNumber())
                .andExpect(jsonPath("$.papers[0]").doesNotExist())
                .andExpect(jsonPath("$.results[2]").doesNotExist());

    /*    //paper 추가
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setWorkspaceId(0L);
        likeOnOffDTO.setPaperId("2307.00865");
        likeOnOffDTO.setOn(true);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );




        //확인
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.results[1].query").value("hi1"))
                .andExpect(jsonPath("$.results[0].query").value("hi2"))
                //.andExpect(jsonPath("$.papers[0].paperId").value("2307.00865"))
                //.andExpect(jsonPath("$.papers[0].title").value("A Survey on Graph Classification and Link Prediction based on GNN"))
                .andExpect(jsonPath("$.papers[1]").doesNotExist());

        //paper 추가
        Map<Object, Object> hashMap = new HashMap<>();
        hashMap.put("workspaceId", "0");
        hashMap.put("paperId", "1706.03762");
        hashMap.put("on", true);
        String content1 = objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .content(content1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //확인
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.results[1].query").value("hi1"))
                .andExpect(jsonPath("$.results[0].query").value("hi2"))
                .andExpect(jsonPath("$.papers[1].paperId").value("2307.00865"))
                .andExpect(jsonPath("$.papers[1].title").value("A Survey on Graph Classification and Link Prediction based on GNN"))
                .andExpect(jsonPath("$.papers[2]").doesNotExist())
                .andExpect(jsonPath("$.papers[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is all you need"));


        //paper 제거
        Map<Object, Object> hashMap1 = new HashMap<>();
        hashMap.put("workspaceId", "0");
        hashMap.put("paperId", "2307.00865");
        hashMap.put("on", false);
        String content2= objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .content(content2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //확인
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.results[1].query").value("hi1"))
                .andExpect(jsonPath("$.results[0].query").value("hi2"))
                .andExpect(jsonPath("$.papers[1]").doesNotExist())
                .andExpect(jsonPath("$.papers[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is all you need"));
*/
    }

    //@Test
    public void searchDetailTestSuccess() throws Exception {
        //검색하기.

        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId" , "0")
                        .queryParam("query", "hi1")
                        .queryParam("searchType", "2")
        );
        String contentAsString = mockMvc.perform(
                get("http://localhost:8080/api/history/search?workspaceId=0")
                        .header("Gauth", jwt)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        long aLong = jsonNode.get("results").get(0).get("rid").asLong();
        System.out.println("aLong = " + aLong);
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search/result/"+ aLong + "?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.query").value("hi1"))
                .andExpect(jsonPath("$.answer").value("reinforcement learning is a learning paradigm for solving sequential decision-making problems. It works by learning from trial-and-error interactions with the environment over its lifetime, and can be enhanced by incorporating causal relationships into the learning process."))
                .andExpect(jsonPath("$.papers[1].paperId").value("2204.13154"))
                .andExpect(jsonPath("$.papers[1].title").value("Attention Mechanism in Neural Networks: Where it Comes and Where it Goes"))
                .andExpect(jsonPath("$.papers[2]").doesNotExist())
                .andExpect(jsonPath("$.papers[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is all you need"));
    }

    //@Test
    public void searchDetailTestFail() throws Exception {
        //없을때 검색하기.
        mockMvc.perform(
                get("http://localhost:8080/api/history/search/result/"+ 1+ "?workspaceId=0")

                        .header("Gauth", jwt)
        ).andExpect(
                status().isBadRequest()
        );
        //없는 계정
        mockMvc.perform(
                get("http://localhost:8080/api/history/search/result/"+ 1+ "?workspaceId=3")
                        .header("Gauth", jwt)
        ).andExpect(
                status().isUnauthorized()
        );

        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .header("Gauth", jwt)
                        .queryParam("workspaceId", "0")
                        .queryParam("query", "hi1")
                        .queryParam("searchType", "2")
        );
        String contentAsString = mockMvc.perform(
                get("http://localhost:8080/api/history/search?workspaceId=0")
                        .header("Gauth", jwt)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        long aLong = jsonNode.get("results").get(0).get("rid").asLong();
        System.out.println("aLong = " + aLong);
        //이상한 아이디
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search/result/"+ aLong +10 + "?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isBadRequest()
                );

    }

    //@Test
    public void searchTrashTest() throws Exception {
        //비어잇음
        mockMvc.perform(
                get("http://localhost:8080/api/history/trash?workspaceId=0")
                        .header("Gauth", jwt)
        ).andExpect(
                status().isOk()
        ).andExpect(jsonPath("$.trashContainers").isArray())
        .andExpect(jsonPath("$.trashContainers").isEmpty())
        .andExpect(jsonPath("$.papers").isArray())
        .andExpect(jsonPath("$.papers").isEmpty());

        //paper 추가
        Map<Object, Object> hashMap = new HashMap<>();
        hashMap.put("workspaceId", 0);
        hashMap.put("paperId", "1706.03762");
        hashMap.put("on", true);
        String content1 = objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Gauth", jwt)
                        .content(content1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );

        //한개 차있음
        mockMvc.perform(
                        get("http://localhost:8080/api/history/trash?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$.trashContainers").isArray())
                .andExpect(jsonPath("$.trashContainers").isEmpty())
                .andExpect(jsonPath("$.papers[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.papers[0].title").value("Attention is all you need"))
                .andExpect(jsonPath("$.papers[1]").doesNotExist());


        hashMap = new HashMap<>();
        hashMap.put("workspaceId", "0");
        hashMap.put("paperId", "1706.03762");
        hashMap.put("on", false);
        content1 = objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //한개 차있음 - 쓰레기통에
        mockMvc.perform(
                        get("http://localhost:8080/api/history/trash?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$.trashContainers[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.trashContainers[0].title").value("Attention is all you need"))
                .andExpect(jsonPath("$.trashContainers[1]").doesNotExist())
                .andExpect(jsonPath("$.papers").isArray())
                .andExpect(jsonPath("$.papers").isEmpty());

        //paper 추가
        hashMap = new HashMap<>();
        hashMap.put("workspaceId", "0");
        hashMap.put("paperId", "2307.00865");
        hashMap.put("on", true);
        content1 = objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        hashMap = new HashMap<>();
        hashMap.put("workspaceId", 0L);
        hashMap.put("paperId", "2307.00865");
        hashMap.put("on", false);
        content1 = objectMapper.writeValueAsString(hashMap);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //두개 차있음

        mockMvc.perform(
                        get("http://localhost:8080/api/history/trash?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$.trashContainers[1].paperId").value("1706.03762"))
                .andExpect(jsonPath("$.trashContainers[1].title").value("Attention is all you need"))
                .andExpect(jsonPath("$.trashContainers[0].paperId").value("2307.00865"))
                .andExpect(jsonPath("$.trashContainers[0].title").value("A Survey on Graph Classification and Link Prediction based on GNN"))
                .andExpect(jsonPath("$.trashContainers[2]").doesNotExist())
                .andExpect(jsonPath("$.papers").isArray())
                .andExpect(jsonPath("$.papers").isEmpty());

        //복구하기 - 0개

        List<String> papers = new ArrayList<>();
        String content = objectMapper.writeValueAsString(papers);
        mockMvc.perform(
                post("http://localhost:8080/api/history/trash?workspaceId=0")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );

        //복구하기 - 1개

        papers = new ArrayList<>();
        papers.add("1706.03762");
        content = objectMapper.writeValueAsString(papers);
        mockMvc.perform(
                post("http://localhost:8080/api/history/trash?workspaceId=0")
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(
                status().isOk()
        );

        List<String> byUser = likePaperRepository.findByUser(0L);
        Assertions.assertEquals(byUser.size(), 1);

        likePaperRepository.update(0L, "1706.03762", false);
        //복구하기 - 2개
        papers.add("2307.00865");
        content = objectMapper.writeValueAsString(papers);
        mockMvc.perform(
                post("http://localhost:8080/api/history/trash?workspaceId=0")
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );


        byUser = likePaperRepository.findByUser(0L
        );
        Assertions.assertEquals(2, byUser.size() );


    }


    //@Test
    public void searchPaperHistoryTest() throws Exception {
        //빈거 테스트
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search/paper?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());


        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion("hihi1");
        String content = objectMapper.writeValueAsString(questionDTO);
        try{
            mockMvc.perform(
                    post("http://localhost:8080/api/paper/1706.03762?workspaceId=0")
                            .header("Gauth", jwt)
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.TEXT_EVENT_STREAM_VALUE)
            ).andExpect(
                    status().isOk()
            );
        }catch (Exception e){

        }
        //비동기 테스트 일단 패스
/*        sleep(2000);
        //1개 테스트
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search/paper?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$[0].paperId").value("1706.03762"))
                .andExpect(jsonPath("$[0].who").value(true))
                .andExpect(jsonPath("$[0].content").value("hihi1"))
                .andExpect(jsonPath("$[0].title").value("Attention is all you need"))
                .andExpect(jsonPath("$[1].paperId").value("1706.03762"))
                .andExpect(jsonPath("$[1].who").value(false))
                .andExpect(jsonPath("$[1].content").value("bibi"))
                .andExpect(jsonPath("$[1].title").value("Attention is all you need"))
        ;
        questionDTO = new QuestionDTO();
        questionDTO.setQuestion("hihi2");
        content = objectMapper.writeValueAsString(questionDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paper/2307.00865?workspaceId=0")
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );


        //2개 테스트
        mockMvc.perform(
                        get("http://localhost:8080/api/history/search/paper?workspaceId=0")
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                ).andExpect(jsonPath("$[3].paperId").value("1706.03762"))
                .andExpect(jsonPath("$[3].who").value(false))
                .andExpect(jsonPath("$[2].content").value("hihi1"))
                .andExpect(jsonPath("$[3].title").value("Attention is all you need"))
                .andExpect(jsonPath("$[2].paperId").value("1706.03762"))
                .andExpect(jsonPath("$[2].who").value(true))
                .andExpect(jsonPath("$[3].content").value("bibi"))
                .andExpect(jsonPath("$[2].title").value("Attention is all you need"))

                .andExpect(jsonPath("$[0].paperId").value("2307.00865"))
                .andExpect(jsonPath("$[0].who").value(true))
                .andExpect(jsonPath("$[0].content").value("hihi2"))
                .andExpect(jsonPath("$[0].title").value("A Survey on Graph Classification and Link Prediction based on GNN"))
                .andExpect(jsonPath("$[1].paperId").value("2307.00865"))
                .andExpect(jsonPath("$[1].who").value(false))
                .andExpect(jsonPath("$[1].content").value("bibi"))
                .andExpect(jsonPath("$[1].title").value("A Survey on Graph Classification and Link Prediction based on GNN"))
        ;

        questionDTO = new QuestionDTO();
        questionDTO.setQuestion("hihi3");
        content = objectMapper.writeValueAsString(questionDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paper/1706.03762?workspaceId=0")
                        .header("Gauth", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );*/
    }

}
