package com.example.yeondodemo.httptest;

import com.example.yeondodemo.Controller.PaperController;
import com.example.yeondodemo.ControllerAsnc.AsyncPaperController;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.filter.AspectController;
import com.example.yeondodemo.repository.etc.BatisAuthorRepository;
import com.example.yeondodemo.repository.history.BatisSearchHistoryRepository;
import com.example.yeondodemo.repository.history.QueryHistoryRepository;
import com.example.yeondodemo.repository.paper.*;
import com.example.yeondodemo.repository.paper.batis.BatisPaperBufferRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperInfoRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperRepository;
import com.example.yeondodemo.repository.history.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.studyfield.BatisStudyFieldRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisLikePaperRepository;
import com.example.yeondodemo.repository.user.batis.BatisRealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisUserRepository;
import com.example.yeondodemo.repository.user.LikePaperRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.service.login.TokenType;
import com.example.yeondodemo.service.search.PaperService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.Updater;
import com.example.yeondodemo.validation.WorkspaceValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.utils.installFastApi;
import static util.utils.isFastApiInstalled;

@EnableAspectJAutoProxy
@WebMvcTest({PaperController.class, AsyncPaperController.class}) @AutoConfigureWebMvc
@AutoConfigureMybatis
@WithMockUser
@Import({AspectController.class, SecurityConfig.class, AspectController.class, BatisRealUserRepository.class, JwtTokenProvider.class,BatisSearchHistoryRepository.class, Updater.class, BatisAuthorRepository.class, BatisPaperBufferRepository.class, BatisPaperInfoRepository.class, BatisQueryHistoryRepository.class, PaperService.class, BatisPaperRepository.class, BatisLikePaperRepository.class, BatisUserRepository.class, BatisStudyFieldRepository.class, BatisLikePaperRepository.class})
public class HttpPaperTest {

    @InjectMocks
    private PaperService paperService;
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
    PlatformTransactionManager transactionManager;
    @Autowired
    BatisAuthorRepository authorRepository;
    @Autowired
    Updater updater;

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
    public void beforeEach(){
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
        studyFieldRepository.save("1234");
        studyFieldRepository.save("12345");
        studyFieldRepository.save("12346");
        studyFieldRepository.save("12347");
//        Paper paper1 = new Paper("1706.03762");
//        //paper1.setAbs("The dominant sequence transduction models are based on complex recurrent or convolutional neural networks that include an encoder and a decoder. The best performing models also connect the encoder and decoder through an attention mechanism. We propose a new simple network architecture, the Transformer, based solely on attention mechanisms, dispensing with recurrence and convolutions entirely. Experiments on two machine translation tasks show these models to be superior in quality while being more parallelizable and requiring significantly less time to train. Our model achieves 28.4 BLEU on the WMT 2014 Englishto-German translation task, improving over the existing best results, including ensembles, by over 2 BLEU. On the WMT 2014 English-to-French translation task, our model establishes a new single-model state-of-the-art BLEU score of 41.0 after training for 3.5 days on eight GPUs, a small fraction of the training costs of the best models from the literature.");
//        paper1.setCites(81860);
//        paper1.setAuthors(new ArrayList<String>(Arrays.asList("Ashish Vaswani", "Noam Shazeer", "Niki Parmar", "Jakob Uszkoreit", "Llion Jones", "Aidan N. Gomez", "Łukasz Kaiser", "Illia Polosukhin")));
//        paper1.setUrl("https://arxiv.org/abs/1706.03762");
//        paper1.setConference("2017 - proceedings.neurips.cc");
//        paper1.setTitle("Attention is all you need");
//        paper1.setYear(2017);
//        paper1.setLikes(200);
//        //paper1.setSummary("The dominant sequence transduction models are based on complex recurrent or convolutional neural networks in an encoder-decoder configuration. The best performing models also connect the encoder and decoder through an attention mechanism. We propose a new simple network architecture, the Transformer, based solely on attention mechanisms, dispensing with recurrence and convolutions entirely. Experiments on two machine translation tasks show these models to be superior in quality while being more parallelizable and requiring significantly less time to train. Our model achieves 28.4 BLEU on the WMT 2014 English-to-German translation task, improving over the existing best results, including ensembles by over 2 BLEU. On the WMT 2014 English-to-French translation task, our model establishes a new single-model state-of-the-art BLEU score of 41.8 after training for 3.5 days on eight GPUs, a small fraction of the training costs of the best models from the literature. We show that the Transformer generalizes well to other tasks by applying it successfully to English constituency parsing both with large and limited training data.");
        Paper paper2 = new Paper("2204.13154");
        paper2.setSummary("A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n");
//        //paper2.setAbs("A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n");
        paper2.setAuthors(new ArrayList<String>(Arrays.asList("Derya Soydaner")));
//        //paper2.setUrl("https://arxiv.org/abs/2204.13154");
        paper2.setTitle("Attention Mechanism in Neural Networks: Where it Comes and Where it Goes");
//        paper2.setCites(170);
//        paper2.setConference("2021 - proceedings.mlr.press");
//        paper2.setYear(2021);
//        paper2.setLikes(12);
//        int i=0;
//        Paper[] papers= new Paper[20];
//        papers[i++] = new Paper("2307.00865");
//        papers[i++] =  new Paper("2010.01369");
//        papers[i++] =  new Paper("2204.05437");
//        papers[i++] =  new Paper("1903.08131");
//        papers[i++] =  new Paper("1707.02725");
//        papers[i++] =  new Paper("2111.00977");
//        papers[i++] =  new Paper("2212.09507");
//        papers[i++] =  new Paper("1404.5997");
//        papers[i++] =  new Paper("2203.12114");
//        papers[i++] =  new Paper("1909.02765");
//        papers[i++] =  new Paper("2001.09608");
//        papers[i++] =  new Paper("2212.00253");
//        papers[i++] =  new Paper("2108.11510");
//        papers[i++] =  new Paper("2009.07888");
//        papers[i++] =  new Paper("2105.10559");
//        papers[i++] =  new Paper("2202.05135");
//        papers[i++] =  new Paper("1909.13474");
//        papers[i++] =  new Paper("2307.01452");
//        papers[i++] =  new Paper("2108.03258");
//        papers[i++] =  new Paper("2204.11897");
//        paperRepository.save(paper1);
//        paperRepository.save(paper2);
//        for(String author : paper1.getAuthors()){
//            authorRepository.save(paper1.getPaperId(), author);
//        }
//        for (Paper paper : papers) {
//            paperRepository.save(paper);
//        }
//        String user ="testtest1";
//        likePaperRepository.save(user, paper1.getPaperId(), true);
//        likePaperRepository.save(user, papers[0].getPaperId(), true);
//        likePaperRepository.save(user, papers[2].getPaperId(), true);
//        likePaperRepository.save(user, papers[4].getPaperId(), true);
//        likePaperRepository.save(user, papers[6].getPaperId(), true);
//        likePaperRepository.save(user, papers[10].getPaperId(), true);
//        likePaperRepository.save(user, papers[12].getPaperId(), true);
//        likePaperRepository.save(user, papers[15].getPaperId(), true);
//        likePaperRepository.save(user, paper1.getPaperId(), true);
//        paperBufferRepository.save(new PaperBuffer(paper1.getPaperId()));
    }
    @AfterEach
    public void afterEach(){
        transactionManager.rollback(status);
    }
    ////@Test
    public void test2dot2Success() throws Exception {
        String paperId = "1706.03762";
        String email="test@test.com";
        Long workspaceId = 0L;
        System.out.println("jwt = " + jwt);
        System.out.println("WorkspaceValidator.login = " + WorkspaceValidator.login);
        mockMvc.perform(
                        get("http://localhost:8080/api/paper/1706.03762?workspaceId={workspaceId}",workspaceId)
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.paperInfo.title").value("Attention is all you need"))
                .andExpect(jsonPath("$.paperInfo.year").value(2017))
                .andExpect(jsonPath("$.paperInfo.url").value("https://arxiv.org/abs/1706.03762"))
                .andExpect(jsonPath("$.paperInfo.authors[0]").value("Ashish Vaswani"))
                .andExpect(jsonPath("$.paperInfo.authors[1]").value("Noam Shazeer"))
                .andExpect(jsonPath("$.paperInfo.authors[2]").value("Niki Parmar"))
                .andExpect(jsonPath("$.paperInfo.cites").isNumber())
              //  .andExpect(jsonPath("$.paperinfo.summary").value("A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n"))
                .andExpect(jsonPath("$.paperInfo.questions[0]").value("gpt가 만든 질문 1"))
                .andExpect(jsonPath("$.paperInfo.questions[1]").value("gpt가 만든 질문 2"))
                .andExpect(jsonPath("$.paperInfo.subjectRecommends[0]").value("향후 연구 주제 추천 1"))
                .andExpect(jsonPath("$.paperInfo.subjectRecommends[1]").value("향후 연구 주제 추천 2"))
                .andExpect(jsonPath("$.paperInfo.subjectRecommends[2]").value("향후 연구 주제 추천 3"));
               // .andExpect(jsonPath("$.paperinfo.references[0]").value("ref1"))
               // .andExpect(jsonPath("$.paperinfo.references[2]").value("ref3"));
    }
    ////@Test
    public void test2dot2Fail() throws Exception {
        //wrong paperid
        String paperId = "1706.03763";
        Long workspaceId = 0L;
        mockMvc.perform(
                        get("http://localhost:8080/api/paper/{paperId}?worksapceId={workspaceId}", paperId, workspaceId)
                                .header("Gauth", jwt)
                ).andExpect(
                        status().isBadRequest()
                );
        //wrong user
        paperId = "1706.03762";
        workspaceId = 3L;
        mockMvc.perform(
                get("http://localhost:8080/api/paper/{paperId}?workspaceId={workspaceId}", paperId, workspaceId)
                        .header("Gauth", jwt+"1")
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Autowired
    private WebTestClient webTestClient;
    ////@Test
    public void test2dot3_InPaperQuerySuccess() throws Exception {
        //포기
/*        String paperId = "1706.03762";
        Long workspaceId = 0L;
        Map<String, String> question = new HashMap<>();
        question.put("question", "what is Question?");
        String content = objectMapper.writeValueAsString(question);
        MvcResult mvcResult = mockMvc.perform(
                post("http://localhost:8080/api/paper/{paperId}?workspaceId={wokspaceId}", paperId, workspaceId)
                        .header("Gauth", jwt)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_EVENT_STREAM_VALUE)
        ).andExpect(
                status().isOk()
        ).andReturn();
        Thread.sleep(2000);
        Flux<ServerSentEvent<String>> fluxResponse = (Flux<ServerSentEvent<String>>) mvcResult.getAsyncResult();

        StepVerifier.create(fluxResponse)
                .expectNextCount(3) // 예상되는 Flux 요소 수
                .expectComplete()
            .verify();
                mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andExpect(jsonPath("$.length()").value(3));*/


    }




}
