package com.example.yeondodemo;

import com.example.yeondodemo.configuration.RedisConfig;
import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import com.example.yeondodemo.repository.etc.batis.BatisKeywordRepository;
import com.example.yeondodemo.repository.history.BatisSearchHistoryRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperRepository;
import com.example.yeondodemo.repository.paper.batis.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.studyfield.BatisStudyFieldRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisLikePaperRepository;
import com.example.yeondodemo.repository.user.batis.BatisRealUserRepository;
import com.example.yeondodemo.repository.user.batis.BatisUserRepository;
import com.example.yeondodemo.service.login.LoginService;
import com.example.yeondodemo.utils.JwtTokenProvider;
import com.example.yeondodemo.utils.Updater;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootTest
public class RedisRepoTest {
    @Autowired
    private RefreshRedisRepository repository;
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    public void beforeEach() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }
    @AfterEach
    public void afterEach(){
        transactionManager.rollback(status);
    }


    //@Test
    void test(){
        RefreshEntity refreshEntity = new RefreshEntity("1234", "abcd1234");
        repository.save(refreshEntity);
        System.out.println(repository.findById("1234"));
    }
}
