package com.example.yeondodemo.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleRequest {
    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String responseType;
    private String scope;
    private String code;
    private String accessType="offline";
    private String grantType;
    private String state;
    private String includeGrantedScopes="true";
    private String loginHint;
    private String prompt;
}
