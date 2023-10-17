package com.example.yeondodemo;

import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.repository.etc.RefreshRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
