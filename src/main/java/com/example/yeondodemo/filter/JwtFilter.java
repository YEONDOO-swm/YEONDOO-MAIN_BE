package com.example.yeondodemo.filter;

import com.example.yeondodemo.utils.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
    //헤더에서 받아올 이름 지정
    public static final String AUTHORIZATION_HEADER = "Gauth";
    private final JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwtToken = resolveToken(httpServletRequest);
        log.info("jwt" + jwtToken);
        String requestURI = httpServletRequest.getRequestURI();
        if(StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
            //토큰 값에서 Authentication 값으로 가공해서 반환 후 저장
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다. requestURI : {}", requestURI);
        }

        //다음 필터로 넘기기
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken)) return bearerToken;
        return null;
    }
}
