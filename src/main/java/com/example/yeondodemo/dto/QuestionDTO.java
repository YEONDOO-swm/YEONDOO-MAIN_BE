package com.example.yeondodemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionDTO {
        @Size(min=1, max = 300) @NotNull
        private String question;
        public String getQuestion(){return question;}
        public void setQuestion(String q){this.question = q;}
    }