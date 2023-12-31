package com.example.yeondodemo.httptest;

import com.example.yeondodemo.Controller.LoginController;
import com.example.yeondodemo.entity.Workspace;
import com.example.yeondodemo.repository.etc.batis.BatisKeywordRepository;
import com.example.yeondodemo.repository.history.BatisSearchHistoryRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperRepository;
import com.example.yeondodemo.repository.history.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.studyfield.BatisStudyFieldRepository;
import com.example.yeondodemo.repository.studyfield.StudyFieldRepository;
import com.example.yeondodemo.repository.user.*;
import com.example.yeondodemo.repository.user.batis.BatisLikePaperRepository;
import com.example.yeondodemo.repository.user.batis.BatisRealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisUserRepository;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.service.login.TokenType;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.Updater;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(LoginController.class)@AutoConfigureMybatis
@AutoConfigureWebMvc @Import({BatisRealUserRepository.class, JwtTokenProvider.class,Updater.class, BatisQueryHistoryRepository.class,BatisKeywordRepository.class, BatisSearchHistoryRepository.class, LoginService.class, BatisUserRepository.class, BatisStudyFieldRepository.class, BatisPaperRepository.class, BatisLikePaperRepository.class})
public class HttpLoginTest {
    @InjectMocks
    private LoginService loginService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudyFieldRepository studyFieldRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @Autowired
    RealUserRepository realUserRepository;
    @Autowired
    JwtTokenProvider provider;
    String jwt;
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
    }
    @AfterEach
    public void afterEach(){
        transactionManager.rollback(status);
    }

    //@Test
    public void loginSuccessTest() throws Exception {
    }
    //@Test
    public void loginFailNotPasswordNotMatch() throws Exception {

    }
    //@Test
    public void loginFailNotPasswordValidationError() throws Exception {
    }
    //@Test
    public void loginSuccessAndNotFirstCase() throws Exception {

    }
    //@Test
    public void studyFieldTestSuccess() throws Exception {
        List<String> fields = new ArrayList<String>(Arrays.asList("abcd","abcd5","abcd6","abcd7"));
        /*mockMvc.perform(
                        get("http://localhost:8080/api/userprofile/testtest1")
                ).andExpect(
                        status().isOk()
                )
                .andExpect(jsonPath("$.fields").isArray())
                .andExpect(jsonPath("$.fields.length()").value(4))
                .andExpect(jsonPath("$.fields[0]").value("abcd"))
                .andExpect(jsonPath("$.fields[1]").value("abcd5"))
                .andExpect(jsonPath("$.fields[2]").value("abcd6"))
                .andExpect(jsonPath("$.fields[3]").value("abcd7"))
                .andDo(print());*/

    }
    //@Test
    public void studyFieldTestFailNOTFOUND() throws Exception {
        /*mockMvc.perform(
                get("http://localhost:8080/userprofile/api/testtest3")
        ).andExpect(
                status().isNotFound()
        ).andDo(print());*/
    }
    //@Test
    public void studyFieldTestFailUNAUTHORIZED() throws Exception {
/*        mockMvc.perform(
                get("http://localhost:8080/api/userprofile/testtest2")
        ).andExpect(
                status().isUnauthorized()
        ).andDo(print());*/
    }
    //@Test
    public void studyFieldPostSuccess() throws Exception {
        /*UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setStudyField("HI");
        userProfileDTO.setKeywords(Arrays.asList("1","2","3"));
        userProfileDTO.setUsername("testtest1");
        String content = objectMapper.writeValueAsString(userProfileDTO);
        mockMvc.perform(
                post("http://localhost:8080/api/userprofile")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status().isOk()
        ).andDo(print());*/
    }
    //@Test
    public void studyFieldPostFailBadRequest() throws Exception {
        /*UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setStudyField("");
        userProfileDTO.setKeywords(Arrays.asList("1","2","3"));
        userProfileDTO.setUsername("testtest1");
        String content = objectMapper.writeValueAsString(userProfileDTO);
        mockMvc.perform(
                        post("http://localhost:8080/api/userprofile")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isBadRequest()
                ).andDo(print());*/
    }

}



