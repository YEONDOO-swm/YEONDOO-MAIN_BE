package com.example.yeondodemo.dto.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthenticationDTO {
    private String Gauth;
    private String RefreshToken;
}
