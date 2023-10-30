package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.PythonPaperInfoDTO;
import com.example.yeondodemo.dto.PythonQuestionDTO;
import com.example.yeondodemo.dto.PythonResultDTO;
import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.python.PaperPythonFirstResponseDTO;
import com.example.yeondodemo.dto.python.PythonQuestionResponse;
import com.example.yeondodemo.exceptions.TokenNotEnoughException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
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
        System.out.println("pythonpythonpython"+ response.getBody());
        return response.getBody();
    }
    public static PaperPythonFirstResponseDTO requestPaperInfo(String paperid, String pythonApiServer, Boolean userPdf){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<PaperPythonFirstResponseDTO> response = restTemplate.exchange(
                    pythonApiServer + "/chat?paperId=" + paperid + "&userPdf=" + userPdf
                    ,
                    HttpMethod.GET,
                    null,
                    PaperPythonFirstResponseDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 402) {
                // 402 에러가 발생한 경우 처리할 코드
                throw new TokenNotEnoughException("Not Enough Token");
            }
            // 다른 HTTP 클라이언트 오류 처리
            throw e;
        }
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
        //todo: post담는 부분 수정
        ResponseEntity<PythonQuestionResponse> response = restTemplate.exchange(
                pythonApiServer + "/chat",
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
