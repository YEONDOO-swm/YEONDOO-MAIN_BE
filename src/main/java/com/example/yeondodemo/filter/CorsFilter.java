package com.example.yeondodemo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(OrderedFilter.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "https://yeondoo.net");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, Gauth, RefreshToken");
        response.setHeader("Cross-Origin-Opener-Policy", "same-origin");
        response.setHeader("Access-Control-Expose-Headers","Gauth, RefreshToken" );
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            chain.doFilter(req, res);
        }

    }
}
