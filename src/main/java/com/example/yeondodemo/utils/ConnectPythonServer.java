package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.PythonPaperInfoDTO;
import com.example.yeondodemo.dto.PythonQuestionDTO;
import com.example.yeondodemo.dto.PythonResultDTO;
import com.example.yeondodemo.dto.TestPython;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Getter @Setter @RequiredArgsConstructor
public class ConnectPythonServer {
    //static String pythonApiServer = "http://10.0.128.161:80"; //파이썬 서버 주소 알아내고 수정하기
    public static PythonResultDTO request(String query,Integer searchType, String pythonApiServer){
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PythonResultDTO> response = restTemplate.exchange(
                pythonApiServer + "/process?query="+query + "&searchType=" + searchType,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                PythonResultDTO.class
        );
        return response.getBody();
    }
    public static PythonPaperInfoDTO requestPaperInfo(String paperid, String pythonApiServer){
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PythonPaperInfoDTO> response = restTemplate.exchange(
                pythonApiServer + "/getpaperinfo?paperId="+paperid,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                PythonPaperInfoDTO.class
        );
        return response.getBody();
    }
    public static String question(PythonQuestionDTO pythonQuestionDTO, String pythonApiServer){
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
        ResponseEntity<Map> response = restTemplate.exchange(
                pythonApiServer + "/question",
                HttpMethod.POST,
                request,
                Map.class
        );
        return (String) response.getBody().get("answer");
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
