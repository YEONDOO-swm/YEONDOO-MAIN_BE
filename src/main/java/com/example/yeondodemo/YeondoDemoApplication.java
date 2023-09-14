package com.example.yeondodemo;

import com.example.yeondodemo.validation.WorkspaceValidator;
import com.example.yeondodemo.validation.PaperValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@RequiredArgsConstructor
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class YeondoDemoApplication {
    private final ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(YeondoDemoApplication.class, args);
    }
    @PostConstruct
    public void init(){
        WorkspaceValidator.init(context);
        PaperValidator.init(context);
    }

}
