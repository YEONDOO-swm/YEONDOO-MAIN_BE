package com.example.yeondodemo.dto.paper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @Builder
public class FileUploadResponse {
    private String url;
    private String paperId;
}
