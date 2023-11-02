package com.example.yeondodemo.utils;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class LoginUtil {
    static Integer REFRESH_TOKEN_VALID_MILLISECOND = 3600000;
    public static String createJwt(String userPk, String keyBase64Encoded) {
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
        Claims claims = Jwts.claims().setSubject(userPk).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_MILLISECOND)).build(); // exp

        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }
}