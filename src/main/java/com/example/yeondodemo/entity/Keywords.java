package com.example.yeondodemo.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = "id")
public class Keywords   {
    private Long id;
    private User user;
    private String keyword;
}
