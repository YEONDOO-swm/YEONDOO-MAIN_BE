package com.example.yeondodemo.dto.paper;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter @Setter @ToString
public class PaperResultRequest {
    @NotNull
    private Long id;
    @NotNull @Range(min=0, max = 10)
    private Integer score;
}
