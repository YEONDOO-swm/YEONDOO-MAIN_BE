package com.example.yeondodemo.repository;

import com.example.yeondodemo.dto.PaperHistory;
import com.example.yeondodemo.repository.paper.batis.BatisPaperBufferRepository;
import com.example.yeondodemo.repository.paper.batis.BatisPaperInfoRepository;
import com.example.yeondodemo.repository.paper.batis.BatisQueryHistoryRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class batisTest {
    @Autowired
    PaperRepository paperRepository;
    @Autowired
    BatisPaperBufferRepository paperBufferRepository;
    @Autowired
    BatisPaperInfoRepository paperInfoRepository;
    @Autowired
    BatisQueryHistoryRepository queryHistoryRepository;

    void batis(){

        List<PaperHistory> testtest1 = queryHistoryRepository.findByUsernameAndPaperid("testtest1", "1706.03762");
        System.out.println("testtest1 = " + testtest1);
        System.out.println(paperRepository.findById("1706.03762"));
        System.out.println(paperBufferRepository.isHit("1706.03762"));
    }
}
