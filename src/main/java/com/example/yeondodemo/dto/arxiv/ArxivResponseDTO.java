package com.example.yeondodemo.dto.arxiv;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feed")
public class ArxivResponseDTO {
    private List<ArxivEntryDTO> entries;

    @XmlElement(name = "entry")
    public List<ArxivEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<ArxivEntryDTO> entries) {
        this.entries = entries;
    }
}