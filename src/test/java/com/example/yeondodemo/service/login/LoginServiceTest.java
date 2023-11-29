package com.example.yeondodemo.service.login;

import com.example.yeondodemo.repository.paper.PaperRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
class LoginServiceTest {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PaperRepository paperRepository;
    @DisplayName("토큰을 설정하면 토큰 값이 바뀐다.")
    @Test
    void setLimit() {
        int token1 = 10;
        loginService.setLimit(10);
        int savedToken = paperRepository.getToken();

        assertThat(token1).isEqualTo(savedToken);
        int token2 = 11;
        loginService.setLimit(token2);
        int savedToken2 = paperRepository.getToken();

        assertThat(token2).isEqualTo(savedToken2);


    }
}