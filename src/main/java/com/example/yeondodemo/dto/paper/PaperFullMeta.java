package com.example.yeondodemo.dto.paper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PaperFullMeta{
    //@SerializedName("id")
    @JsonProperty(value="id")
    private String paperId;
    private String submitter;
    private String authors;
    private String title;
    private String comments;
    //@SerializedName("journal-ref")
    @JsonProperty(value="journal-ref")
    private String journalRef;
    private String doi;
    //@SerializedName("report-no")
    @JsonProperty(value="report-no")
    private String reportNo;
    private String categories;
    private String license;
    //@SerializedName("abstract")
    @JsonProperty(value="abstract")
    private String summary;
    private List<Version> versions;
    private String update_date;
    private List<List<String>> authors_parsed;
    private String version;

}
