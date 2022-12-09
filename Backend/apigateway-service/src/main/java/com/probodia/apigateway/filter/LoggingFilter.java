package com.probodia.apigateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter(){ super(Config.class);}

    @Data
    static class Config{
        private String serverName;
    }

    @Override
    public GatewayFilter apply(Config config) {

        OrderedGatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("[{}] Request Start",config.getServerName());

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
//                log.info("[{}] Request End",config.getServerName());
            }));
        }, Ordered.HIGHEST_PRECEDENCE);

        return filter;

    }

}
