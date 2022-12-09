package com.probodia.challengeservice.batch.jobs;

import com.probodia.challengeservice.api.service.ChallengeService;
import com.probodia.challengeservice.batch.tasklets.ChallengeTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AfterChallengeConfig {

    private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용
    private final ChallengeService challengeService;

    // JobBuilderFactory를 통해서 tutorialJob을 생성
    @Bean
    public Job tutorialJob() {
        return jobBuilderFactory.get("challengeJob")
                .start(tutorialStep())  // Step 설정
                .build();
    }

    // StepBuilderFactory를 통해서 tutorialStep을 생성
    @Bean
    public Step tutorialStep() {
        return stepBuilderFactory.get("challengeStep")
                .tasklet(new ChallengeTasklet(challengeService)) // Tasklet 설정
                .build();
    }

}
