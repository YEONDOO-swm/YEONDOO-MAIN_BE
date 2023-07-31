package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.ScholarDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import serpapi.GoogleSearch;
import serpapi.SerpApiSearchException;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ConnectScholar {
    public static ScholarDTO getScholarInfo(String title, String key){
        Map<String, String> parameter = new HashMap<>();

        parameter.put("engine", "google_scholar");
        parameter.put("q", title);
        parameter.put("api_key", key);

        GoogleSearch search = new GoogleSearch(parameter);
        JsonArray organic_results;
        JsonObject organic_result;
        JsonObject publicInfo;
        String conference = "";
        Integer cite = 0;
        try
        {
            JsonObject results = search.getJson();
            organic_results = (JsonArray) results.get("organic_results");
            organic_result = (JsonObject) organic_results.get(0);
            publicInfo = (JsonObject) organic_result.get("publication_info");
            String summary = publicInfo.get("summary").getAsString();
            String [] elements = summary.split(", ");
            conference = elements[elements.length-1];
            log.info("conference = {}", conference);

            JsonObject inlineLinks = (JsonObject) organic_result.get("inline_links");
            JsonObject citedBy = (JsonObject) inlineLinks.get("cited_by");
            cite = citedBy.get("total").getAsInt();
            log.info("cite = " + cite);
        } catch (SerpApiSearchException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
        }
        return new ScholarDTO(conference, cite);
    }


}
