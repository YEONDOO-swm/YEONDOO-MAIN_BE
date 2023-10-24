package com.example.yeondodemo.utils;

import com.example.yeondodemo.Controller.YeondooDbController;
import com.example.yeondodemo.dto.PaperBuffer;
import com.example.yeondodemo.dto.ScholarDTO;
import com.example.yeondodemo.dto.arxiv.ArxivEntryDTO;
import com.example.yeondodemo.dto.arxiv.ArxivResponseDTO;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.repository.paper.mapper.PaperBufferMapper;
import com.example.yeondodemo.repository.paper.mapper.PaperMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component @RequiredArgsConstructor
public class Updater {
    private final PaperMapper paperMapper;
    private final PaperBufferMapper paperBufferMapper;
    @Value("${serpapi.key}") private String key;
    @Transactional
    public Paper save(Paper paper){
        paperMapper.save(new Paper(paper.getPaperId()));
        paperBufferMapper.save(new PaperBuffer(paper.getPaperId(), false));
        return update(paper);
    }
    @Transactional
    public Paper update(Paper paper){
        if(paper.getLastUpdate() == null || ChronoUnit.DAYS.between(paper.getLastUpdate() ,LocalDate.now()) > 7 ){
            ScholarDTO scholarDTO = ConnectScholar.getScholarInfo(paper.getTitle(), key);
            paper.setScholar(scholarDTO);
            paper.setLastUpdate(LocalDate.now());
            paperMapper.update(paper.getPaperId(), paper);
        }
        return paper;
    }
    public class AtomXmlParser {
        public static ArxivResponseDTO parse(String xml) {
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setClassesToBeBound(ArxivResponseDTO.class);

            Source source = new StringSource(xml);
            return (ArxivResponseDTO) marshaller.unmarshal(source);
        }
    }

    public List<Paper> getPaperMeta(List<String> paperList){
        RestTemplate restTemplate = new RestTemplate();
        String xmlUrl = "http://export.arxiv.org/api/query?id_list="; // 실제 Atom 피드 URL로 대체해야 합니다.
        xmlUrl += String.join(",", paperList);
        String xmlResponse = restTemplate.getForObject(xmlUrl, String.class);
        // Atom XML을 DTO로 파싱
        int i = 0;
        ArxivResponseDTO feedResponse = YeondooDbController.AtomXmlParser.parse(xmlResponse);
        List<Paper> papers = new ArrayList<>();
        for (ArxivEntryDTO entry : feedResponse.getEntries()) {
            Paper paper = new Paper(entry, paperList.get(i++));
            paperMapper.save(paper);
            paperBufferMapper.save(new PaperBuffer(paper.getPaperId(), false));
            papers.add(paper);
        }
        return papers;

    }
}
