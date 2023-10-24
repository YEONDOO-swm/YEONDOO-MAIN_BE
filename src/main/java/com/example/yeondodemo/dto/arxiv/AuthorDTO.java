package com.example.yeondodemo.dto.arxiv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "author", namespace = "http://www.w3.org/2005/Atom") @ToString
public class AuthorDTO {
    private String name;

    public void setName(String name){
        this.name = name;
    }
    @XmlElement(name = "name", namespace = "http://www.w3.org/2005/Atom")
    public String getName(){
        return this.name;
    }
}
