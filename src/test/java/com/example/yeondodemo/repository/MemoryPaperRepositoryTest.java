package com.example.yeondodemo.repository;

import com.example.yeondodemo.repository.paper.PaperRepository;
import com.example.yeondodemo.entity.Paper;
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
        Paper fullById = paperRepository.findById(paper.getPaperId());
        System.out.println("fullById = " + fullById);
    }


}