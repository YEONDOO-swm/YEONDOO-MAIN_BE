package com.example.yeondodemo.repository.paper.batis;

import com.example.yeondodemo.dto.token.TokenUsageDTO;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.user.RealUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BatisPaperRepositoryTest {
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private RealUserRepository repository;
    @DisplayName("오늘 질문한 개수가 차감되어 반환됩니다. 하나도 질문한게 없으면 남은 토큰만큼")
    @Test
    void findLeftQuestionsByIdtest(){
        String email = "test@test.com";
        repository.save(email);
        int tokenLimit = 10;
        paperRepository.setToken(tokenLimit);

        int leftQuestionsById1 = paperRepository.findLeftQuestionsById(email);

        //질문하기
        LocalDateTime today = LocalDateTime.now();
        TokenUsageDTO tokenDTO = new TokenUsageDTO(email, today);
        paperRepository.saveUsage(tokenDTO);
        int leftQuestionsById2 = paperRepository.findLeftQuestionsById(email);
        //given
        assertThat(leftQuestionsById1).isEqualTo(tokenLimit);
        assertThat(leftQuestionsById2).isEqualTo(tokenLimit-1);
        //when

        //then
    }


    @DisplayName("과거의 질문은 영향을 주지 않습니다. ")
    @Test
    void findLeftQuestionsByIdtest2(){
        String email = "test@test.com";
        repository.save(email);
        int tokenLimit = 10;
        paperRepository.setToken(tokenLimit);

        int leftQuestionsById1 = paperRepository.findLeftQuestionsById(email);

        //질문하기
        LocalDateTime today = LocalDateTime.of(2020, 12,31,12,0);
        TokenUsageDTO tokenDTO = new TokenUsageDTO(email, today);
        paperRepository.saveUsage(tokenDTO);
        int leftQuestionsById2 = paperRepository.findLeftQuestionsById(email);
        //given
        assertThat(leftQuestionsById1).isEqualTo(tokenLimit);
        assertThat(leftQuestionsById2).isEqualTo(tokenLimit);
        //when

        //then
    }
    @DisplayName("토큰을 설정한다. 가장 최근의 토큰 개수가 저장된다. ")
    @Test
    void setAndGetToken() {
        Integer token = 10;
        Integer latestToken = 11;
        //where
        paperRepository.setToken(token);
        paperRepository.setToken(latestToken);
        //when
        int savedToken = paperRepository.getToken();
        assertThat(savedToken).isEqualTo(latestToken);
        //then
    }

    }