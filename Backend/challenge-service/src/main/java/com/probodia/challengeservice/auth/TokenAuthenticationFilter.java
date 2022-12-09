package com.probodia.challengeservice.auth;

import com.probodia.challengeservice.auth.token.AuthTokenProvider;
import com.probodia.challengeservice.api.exception.UnAuthorizedException;
import com.probodia.challengeservice.auth.token.AuthToken;
import com.probodia.challengeservice.auth.token.AuthTokenProvider;
import com.probodia.challengeservice.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.AuthenticationException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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

        log.info("Token : {}",tokenStr);

        if (token.validate()) {
            Authentication authentication = null;
            try {
                authentication = tokenProvider.getAuthentication(token);
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
        else throw new UnAuthorizedException("Full authentication is required to access this resource");

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
