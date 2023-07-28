package com.example.yeondodemo.repository;

import com.example.yeondodemo.repository.paper.BatisPaperRepository;
import com.example.yeondodemo.repository.paper.MemoryPaperRepository;
import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryPaperRepositoryTest {
    @Autowired
    PaperRepository paperRepository;

    void findById() {
        Paper paper = new Paper("1706.03762");
        //paperRepository.save(paper);
        //assertThat(paperRepository.findById(paper.getPaperId())).isEqualTo(paper);
        Paper fullById = paperRepository.findFullById(paper.getPaperId());
        System.out.println("fullById = " + fullById);
    }


}