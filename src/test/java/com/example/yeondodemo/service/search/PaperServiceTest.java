package com.example.yeondodemo.service.search;

import com.example.yeondodemo.repository.paper.PaperRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaperServiceTest {
    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperRepository paperRepository;


    @Test
    void getToken() {
    }
}