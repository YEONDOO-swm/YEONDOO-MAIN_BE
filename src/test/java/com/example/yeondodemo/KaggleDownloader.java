package com.example.yeondodemo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class KaggleDownloader {
    public static void main(String[] args){
        String paperId = "1706.03762";
        String directoryPath = "pdfs";
        String fileName = "sample.pdf";
        String base = "https://arxiv.org/pdf/" + paperId + "v";


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            for(int v = 10; v>0; v--){
                String pdfUrl =base + v;
                HttpGet request1 = new HttpGet(pdfUrl);
                HttpResponse response2 = httpClient.execute(request1);

                // 디렉토리가 없는 경우 생성
                Path directory = Paths.get(directoryPath);
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }

                String filePath = Paths.get(directoryPath, fileName).toString();
                int i = 0;

                try (InputStream inputStream = response2.getEntity().getContent();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        i++;
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                if(i>30){break;}


            }
            HttpPost request = new HttpPost("http://localhost:8000/upload"); // FastAPI 엔드포인트 URL
            File pdfFile = new File(directoryPath, fileName);
            CloseableHttpResponse response;
            try (FileInputStream inputStream = new FileInputStream(pdfFile)) {
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                        .addBinaryBody("file", inputStream, ContentType.create("application/pdf"), pdfFile.getName());

                request.setEntity(entityBuilder.build());
                response = httpClient.execute(request);

            }
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // PDF 파일 삭제
                if (pdfFile.exists()) {
                    pdfFile.delete();
                    System.out.println("PDF 파일이 삭제되었습니다.");
                }
            } else {
            }
    } catch (IOException e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e);
        }

    }
}
