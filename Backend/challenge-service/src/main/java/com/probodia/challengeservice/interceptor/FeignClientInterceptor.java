package com.probodia.challengeservice.interceptor;

import com.probodia.challengeservice.auth.token.AuthToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null) {
//            log.info("AUTH : {}, {}, {}",authentication.getAuthorities(),authentication.getCredentials(),authentication.getDetails());
//            AuthToken token = (AuthToken) authentication.getCredentials();
//            log.info("TOKEN : {}",token.getToken());
//            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token.getToken()));
//        }
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzQzMzQxMTAxIiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTAzMDA0NjEwNDl9.ZucTw5D1RSfLqbPkG_PfiyOkppO6SH1B6BFJNjsHzuA"));
    }
}