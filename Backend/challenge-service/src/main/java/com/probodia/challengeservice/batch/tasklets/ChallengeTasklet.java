package com.probodia.challengeservice.batch.tasklets;


import com.probodia.challengeservice.api.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class ChallengeTasklet implements Tasklet {

    private final ChallengeService challengeService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        log.info("Hello tasklet!!");
        challengeService.processChallengeAfter();

        return RepeatStatus.FINISHED;
    }
}
