package com.example.yeondodemo.validation;

public class SearchValidator {
    public static boolean isNotValidateSearchQuery(String query){
        return query == null || query.length()>300 || query == "";
    }
}
