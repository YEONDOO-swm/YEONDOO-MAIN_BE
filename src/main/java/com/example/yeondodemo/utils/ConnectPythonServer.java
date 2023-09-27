package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.PythonPaperInfoDTO;
import com.example.yeondodemo.dto.PythonQuestionDTO;
import com.example.yeondodemo.dto.PythonResultDTO;
import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Getter @Setter @RequiredArgsConstructor
public class ConnectPythonServer {
    //static String pythonApiServer = "http://10.0.128.161:80"; //파이썬 서버 주소 알아내고 수정하기
    public static PythonResultDTO request(String query,Integer searchType, String pythonApiServer){
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PythonResultDTO> response = restTemplate.exchange(
                pythonApiServer + "/papers?query="+query,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                PythonResultDTO.class
        );
        return response.getBody();
    }
    public static String requestPaperInfo(String paperid, String pythonApiServer){
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                pythonApiServer + "/chat?paperId="+paperid,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String.class
        );
        return response.getBody();
    }
    public static PythonQuestionResponse question(PythonQuestionDTO pythonQuestionDTO, String pythonApiServer){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(pythonQuestionDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        // HTTP POST 요청 보내기
        ResponseEntity<PythonQuestionResponse> response = restTemplate.exchange(
                pythonApiServer + "/question",
                HttpMethod.POST,
                request,
                PythonQuestionResponse.class
        );
        return response.getBody();
    }
    public static Flux questionStream(PythonQuestionDTO pythonQuestionDTO, String pythonApiServer){
        return WebClient.create()
                .post()
                .uri(pythonApiServer + "/question")
                .retrieve()
                .bodyToFlux(String.class);
    }

    public static TestPython getMeta(String paperId, String pythonApiServer){
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TestPython > response = restTemplate.exchange(
                pythonApiServer + "/db/paper/metainfo?paperId=" + paperId,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                TestPython.class
        );
        return response.getBody();

    }
}
