package com.example.yeondodemo.dto.arxiv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)@Getter @Setter @ToString
@XmlRootElement(name = "feed", namespace = "http://www.w3.org/2005/Atom")
public class ArxivResponseDTO {
    @XmlElement(name = "entry", namespace = "http://www.w3.org/2005/Atom")
    private List<ArxivEntryDTO> entries;
}