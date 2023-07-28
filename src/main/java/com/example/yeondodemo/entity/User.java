package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString(exclude = "id")
public class User {
    private Long id;
    private String username;
    private String password;
    private Boolean isFirst=true;
    private String studyField;
    private List<String> keywords=new ArrayList<>();
    private List<Paper> likePapers=new ArrayList<>();
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }
    public User() {

    }
}
