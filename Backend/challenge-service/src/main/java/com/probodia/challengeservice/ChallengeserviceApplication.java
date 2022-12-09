package com.probodia.challengeservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
@EnableJpaAuditing
@EnableBatchProcessing
@EnableScheduling
@EnableFeignClients
public class ChallengeserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeserviceApplication.class, args);
    }
    @PostConstruct
    void init() {
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
