package com.example.yeondodemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString(exclude = "id")
public class LikePaper {
    private Long id;
    private User user;
    private Paper paper;
    private boolean isValid;
}
