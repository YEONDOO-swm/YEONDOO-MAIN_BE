package com.example.yeondodemo.validation;

public class SearchValidator {
    public static boolean isNotValidateSearchQuery(String query, Integer type){
        return query == null || query.length()>300 || query == "" || type == null || type ==0 || type > 2;
    }
}
