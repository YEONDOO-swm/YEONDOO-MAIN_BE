package com.example.yeondodemo.utils;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PDFReferenceExtractor {
    public static void main(String[] args) {
        String pdfUrl = "https://arxiv.org/pdf/1706.03762.pdf";
        String outputPath = "arxiv_paper.pdf";
        String textOutputPath = "arxiv_paper.txt";

        try {
            FileUtils.copyURLToFile(new URL(pdfUrl), new File(outputPath));

            PDDocument document = PDDocument.load(new File(outputPath));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);
            FileUtils.writeStringToFile(new File(textOutputPath), pdfText, "UTF-8");
            document.close();

            Pattern referencePattern = Pattern.compile("(?i)(arXiv:([0-9]{4}[.][0-9]{4,6}|.*[.][0-9]{7}))|(?i)(CoRR, abs/([0-9]{4}[.][0-9]{4,6}|.*[.][0-9]{7}))");
            Matcher matcher = referencePattern.matcher(pdfText);
            while (matcher.find()) {
                String reference = matcher.group(2);
                if(reference==null) reference = matcher.group(4);
                System.out.println(reference);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
