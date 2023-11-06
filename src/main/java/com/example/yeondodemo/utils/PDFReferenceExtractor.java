package com.example.yeondodemo.utils;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PDFReferenceExtractor {
    public static List<String> ExtractReference(String pdfUrl){
        List<String> ret = new ArrayList<>();
        try {
            InputStream pdfInputStream = new URL(pdfUrl).openStream();

            PDDocument document = PDDocument.load(pdfInputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);

            Pattern referencePattern = Pattern.compile("(?i)(arXiv:([0-9]{4}[.][0-9]{4,6}|.*[.][0-9]{7}))|(?i)(CoRR, abs/([0-9]{4}[.][0-9]{4,6}|.*[.][0-9]{7}))");
            Matcher matcher = referencePattern.matcher(pdfText);
            while (matcher.find()) {
                String reference = matcher.group(2);
                if(reference==null) reference = matcher.group(4);
                ret.add(reference);
            }
            document.close();
            IOUtils.closeQuietly(pdfInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;

    }
    public static void main(String[] args) {
        String pdfUrl = "https://arxiv.org/pdf/1706.03761.pdf";

        try {
            // PDF 파일 다운로드
            InputStream pdfInputStream = new URL(pdfUrl).openStream();

            // PDF에서 텍스트 추출
            PDDocument document = PDDocument.load(pdfInputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);

            Pattern referencePattern = Pattern.compile("(?i)(arXiv:([0-9]{4}[.][0-9]{4,6}|.*[/][0-9]{7}))|(?i)(CoRR, abs/([0-9]{4}[.][0-9]{4,6}|.*[/][0-9]{7}))");
            Matcher matcher = referencePattern.matcher(pdfText);
            while (matcher.find()) {
                String reference = matcher.group(2);
                if(reference==null) reference = matcher.group(4);
                System.out.println(reference);
            }
            IOUtils.closeQuietly(pdfInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
