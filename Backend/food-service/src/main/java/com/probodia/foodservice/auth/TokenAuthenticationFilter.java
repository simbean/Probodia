package com.probodia.foodservice.auth;

import com.probodia.foodservice.api.exception.UnAuthorizedException;
import com.probodia.foodservice.auth.token.AuthToken;
import com.probodia.foodservice.auth.token.AuthTokenProvider;
import com.probodia.foodservice.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/api/*")
public class TokenAuthenticationFilter implements Filter {
    private final AuthTokenProvider tokenProvider;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String tokenStr = HeaderUtil.getAccessToken((HttpServletRequest) request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        log.debug("Token : {}",tokenStr);

        if (token.validate()) {
//            Authentication authentication = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
        else throw new UnAuthorizedException("Full authentication is required to access this resource");

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
