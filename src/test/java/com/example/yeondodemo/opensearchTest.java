package com.example.yeondodemo;

import com.example.yeondodemo.utils.ConnectOpenSearch;
import com.example.yeondodemo.utils.ConnectScholar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class opensearchTest {
    @Value("${opensearch.id}") private String id;
    @Value ("${opensearch.pw}") private String pw;
    @Value ("${opensearch.url}") private String url;
    @Value("${serpapi.key}") private String key;
    @Test
    void aa() throws IOException {
        ConnectScholar.getScholarInfo("Attention is all you need", key);
    }
}
