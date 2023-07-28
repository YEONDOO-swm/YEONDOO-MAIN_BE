package com.example.yeondodemo.utils;

import com.example.yeondodemo.dto.ScholarDTO;
import com.google.gson.JsonObject;
import serpapi.GoogleSearch;
import serpapi.SerpApiSearchException;

import java.util.HashMap;
import java.util.Map;

public class ConnectScholar {
    public static ScholarDTO getScholarInfo(){
        Map<String, String> parameter = new HashMap<>();

        parameter.put("engine", "google_scholar");
        parameter.put("q", "Attention is all you need");
        parameter.put("api_key", "199eb10bd7d146a88613751cb2321bee8050d1291d23a756bad94eac09c40b28");

        GoogleSearch search = new GoogleSearch(parameter);
        try
        {
            JsonObject results = search.getJson();
            var organic_results = results.get("organic_results");
            System.out.println("organic_results = " + organic_results);
        } catch (SerpApiSearchException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
