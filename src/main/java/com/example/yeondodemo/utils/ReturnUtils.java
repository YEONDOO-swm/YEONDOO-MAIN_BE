package com.example.yeondodemo.utils;

import java.util.*;

public class ReturnUtils{
    public static <K, V> Map<K, V> mapReturn(K key, V value) {
        Map<K, V> ret = new HashMap<>();
        ret.put(key, value);
        return ret;
    }
}