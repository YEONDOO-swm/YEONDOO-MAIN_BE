package com.example.yeondodemo.dto.arxiv;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)@Getter
@Setter
@ToString
@XmlRootElement(name = "entry", namespace = "http://www.w3.org/2005/Atom")
public class ArxivEntryDTO {
    @XmlElement(name = "id", namespace = "http://www.w3.org/2005/Atom")
    private String id;
    @XmlElement(name = "updated", namespace = "http://www.w3.org/2005/Atom")
    private String updated;
    @XmlElement(name = "published", namespace = "http://www.w3.org/2005/Atom")
    private String published;
    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    private String title;
    @XmlElement(name = "summary", namespace = "http://www.w3.org/2005/Atom")
    private String summary;
    @XmlElement(name = "author", namespace = "http://www.w3.org/2005/Atom")
    private List<AuthorDTO> authors;
    @XmlElement(name = "category", namespace = "http://www.w3.org/2005/Atom")
    private List<CategoryDTO> categories;



}
