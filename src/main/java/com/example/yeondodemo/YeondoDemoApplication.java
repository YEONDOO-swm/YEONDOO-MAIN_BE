package com.example.yeondodemo;

import com.example.yeondodemo.validation.LoginValidator;
import com.example.yeondodemo.validation.PaperValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@RequiredArgsConstructor
@SpringBootApplication
public class YeondoDemoApplication {
    private final ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(YeondoDemoApplication.class, args);
    }
    @PostConstruct
    public void init(){
        LoginValidator.init(context);
        PaperValidator.init(context);
    }

}
