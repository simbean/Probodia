package com.probodia.challengeservice.messagequeue;

import com.probodia.challengeservice.api.service.ChallengeRecordService;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordRequest;
import com.probodia.challengeservice.messagequeue.entity.BSugarResponse;
import com.probodia.challengeservice.messagequeue.entity.MealResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${messageQueue.rabbitMq.queue}")
public class RabbitListener {

    private final ChallengeRecordService challengeRecordService;

    @RabbitHandler
    public void listenerFood(MealResponseVO mealRequest){
//        log.info("MEAL");
        challengeRecordService.createChallengeRecord(mealRequest.getUserId(),
                new ChallengeRecordRequest(2L, mealRequest.toString(), "NO_IMAGE"));

    }

    @RabbitHandler
    public void listenerBSugar(BSugarResponse sugarRequest){
//        log.info("SUGAR : {}",sugarRequest.getBloodSugar());
        log.info("REQUEST : {}",sugarRequest.getUserId());
        challengeRecordService.createChallengeRecord(sugarRequest.getUserId(),
                new ChallengeRecordRequest(1L, sugarRequest.toString(), "NO_IMAGE"));
    }



}
