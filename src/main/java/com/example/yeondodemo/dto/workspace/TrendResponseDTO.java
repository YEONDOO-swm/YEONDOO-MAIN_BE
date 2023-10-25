package com.example.yeondodemo.dto.workspace;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter @Setter @ToString
public class TrendResponseDTO {
    private String title;
    private String date;
    private String url;
    public TrendResponseDTO(String title, LocalDate date, String url){
        this.title = title;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        this.date = df.format(date);
        this.url=url;
    }
}
