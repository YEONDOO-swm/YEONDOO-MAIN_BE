package com.example.yeondodemo.httptest;


import com.example.yeondodemo.Controller.SearchController;
import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import com.example.yeondodemo.dto.paper.Version;
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
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.User;
import com.example.yeondodemo.service.search.SearchService;
import com.example.yeondodemo.utils.Updater;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.utils.installFastApi;
import static util.utils.isFastApiInstalled;

@WebMvcTest(SearchController.class) @AutoConfigureWebMvc @AutoConfigureMybatis
@Import({BatisPaperRepository.class, BatisSearchHistoryRepository.class, Updater.class, BatisAuthorRepository.class, BatisPaperBufferRepository.class, BatisPaperInfoRepository.class, BatisQueryHistoryRepository.class, SearchService.class,  BatisLikePaperRepository.class, BatisUserRepository.class, BatisStudyFieldRepository.class, BatisLikePaperRepository.class})
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
    public void beforeEach(){
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        User user1 = new User("testtest1", "testtest");
        User user2 = new User("testtest2", "testtest");
        user2.setIsFirst(false);
        userRepository.save(user1);
        userRepository.save(user2);
        studyFieldRepository.save("1234");
        studyFieldRepository.save("12345");
        studyFieldRepository.save("12346");
        studyFieldRepository.save("12347");
//        Paper paper1 = new Paper("1706.03762");
//        //paper1.setAbs("The dominant sequence transduction models are based on complex recurrent or convolutional neural networks that include an encoder and a decoder. The best performing models also connect the encoder and decoder through an attention mechanism. We propose a new simple network architecture, the Transformer, based solely on attention mechanisms, dispensing with recurrence and convolutions entirely. Experiments on two machine translation tasks show these models to be superior in quality while being more parallelizable and requiring significantly less time to train. Our model achieves 28.4 BLEU on the WMT 2014 Englishto-German translation task, improving over the existing best results, including ensembles, by over 2 BLEU. On the WMT 2014 English-to-French translation task, our model establishes a new single-model state-of-the-art BLEU score of 41.0 after training for 3.5 days on eight GPUs, a small fraction of the training costs of the best models from the literature.");
//        paper1.setCites(81192);
//        paper1.setAuthors(new ArrayList<String>(Arrays.asList("Ashish Vaswani", "Noam Shazeer", "Niki Parmar", "Jakob Uszkoreit", "Llion Jones", "Aidan N. Gomez", "Łukasz Kaiser", "Illia Polosukhin")));
//        paper1.setUrl("https://arxiv.org/abs/1706.03762");
//        paper1.setConference("2017 - proceedings.neurips.cc");
//        paper1.setTitle("Attention is all you need");
//        paper1.setYear(2017);
//        paper1.setLikes(200);
//        paper1.setSummary("The dominant sequence transduction models are based on complex recurrent or convolutional neural networks in an encoder-decoder configuration. The best performing models also connect the encoder and decoder through an attention mechanism. We propose a new simple network architecture, the Transformer, based solely on attention mechanisms, dispensing with recurrence and convolutions entirely. Experiments on two machine translation tasks show these models to be superior in quality while being more parallelizable and requiring significantly less time to train. Our model achieves 28.4 BLEU on the WMT 2014 English-to-German translation task, improving over the existing best results, including ensembles by over 2 BLEU. On the WMT 2014 English-to-French translation task, our model establishes a new single-model state-of-the-art BLEU score of 41.8 after training for 3.5 days on eight GPUs, a small fraction of the training costs of the best models from the literature. We show that the Transformer generalizes well to other tasks by applying it successfully to English constituency parsing both with large and limited training data.");
//        Paper paper2 = new Paper("2204.13154");
//        paper2.setSummary("A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n");
//        //paper2.setAbs("A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n");
//        paper2.setAuthors(new ArrayList<String>(Arrays.asList("Derya Soydaner")));
//        //paper2.setUrl("https://arxiv.org/abs/2204.13154");
//        paper2.setTitle("Attention Mechanism in Neural Networks: Where it Comes and Where it Goes");
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
        likePaperRepository.save("testtest1", "1706.03762", true);
        mockMvc.perform(
                get("http://localhost:8080/api/homesearch")
                        .queryParam("username", "testtest1")
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
                                .queryParam("username", "testtest1")
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
                        .queryParam("username", "testtest1")
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
                        .queryParam("username", "testtest1")
                        .queryParam("query", s)
                        .queryParam("searchType", "1")
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    public void likeOnTestSuccess() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setUsername("testtest1");
        likeOnOffDTO.setPaperId("2010.01369");
        likeOnOffDTO.setOn(true);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        Assertions.assertThat(likePaperRepository.findByUser("testtest1")).contains("2010.01369");
    }
    @Test
    public void likeOffTestSuccess() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setUsername("testtest1");
        likeOnOffDTO.setPaperId("2307.00865");
        likeOnOffDTO.setOn(false);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        Assertions.assertThat(likePaperRepository.findByUser("testtest1")).doesNotContain("2307.00865");
    }

    @Test
    public void likeTestFailBadRequestTryToOn() throws Exception {
        LikeOnOffDTO likeOnOffDTO = new LikeOnOffDTO();
        likeOnOffDTO.setUsername("testtest1");
        likeOnOffDTO.setPaperId("2307.00865");
        likeOnOffDTO.setOn(true);
        paperRepository.findById("2307.00865");
        likePaperRepository.save("testtest1", "2307.00865", true);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
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
        likeOnOffDTO.setUsername("testtest1");
        likeOnOffDTO.setPaperId("2010.01369");
        likeOnOffDTO.setOn (false);
        String content = objectMapper.writeValueAsString(likeOnOffDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/paperlikeonoff")
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
                        .queryParam("username","testtest2")
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.papers").isEmpty()
        );
        //when notnull
        mockMvc.perform(
                get("http://localhost:8080/api/container")
                        .queryParam("username","testtest1")
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
