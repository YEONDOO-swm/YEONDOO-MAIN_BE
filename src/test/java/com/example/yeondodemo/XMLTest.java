package com.example.yeondodemo;
import com.example.yeondodemo.Controller.YeondooDbController;
import com.example.yeondodemo.dto.arxiv.ArxivResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class XMLTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String xmlUrl = "http://export.arxiv.org/api/query?id_list=1706.03762,1705.03122"; // 실제 Atom 피드 URL로 대체해야 합니다.
        String xmlResponse = restTemplate.getForObject(xmlUrl, String.class);

        // Atom XML을 DTO로 파싱
        ArxivResponseDTO feedResponse = YeondooDbController.AtomXmlParser.parse(xmlResponse);
        System.out.println(feedResponse);


    }
    @XmlRootElement(name = "entry") @Getter @Setter @ToString
    public class EntryDTO {
        private String id;
        private String updated;
        private String published;
        private String title;
        private String summary;
        @XmlElement(name = "id")
        public String getId() {
            return id;
        }
        @XmlElement(name = "updated")
        public String getUpdated() {
            return updated;
        }
        @XmlElement(name = "published")
        public String getPublished() {
            return published;
        }
        @XmlElement(name = "title")
        public String getTitle() {
            return title;
        }
        @XmlElement(name = "summary")
        public String getSummary() {
            return summary;
        }
    }
    @XmlRootElement(name = "feed") @ToString
    public class FeedResponseDTO {
        private List<EntryDTO> entries;

        @XmlElement(name = "entry")
        public List<EntryDTO> getEntries() {
            return entries;
        }

        public void setEntries(List<EntryDTO> entries) {
            this.entries = entries;
        }
    }
}
