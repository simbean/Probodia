package com.probodia.challengeservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
@Configuration
@Getter
public class PointConfig {

    @Value("${challenge.point}")
    private Integer pointPolicy;

}
