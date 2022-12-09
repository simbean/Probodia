package com.probodia.challengeservice.messagequeue;

import com.probodia.challengeservice.config.rabbitmq.RabbitProducerConfig;
import com.probodia.challengeservice.messagequeue.entity.PointVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitProducerConfig rabbitProducerConfig;
    private final RabbitTemplate rabbitTemplate;

    public void sendPoint(PointVO pointVO){
        rabbitTemplate.convertAndSend(rabbitProducerConfig.getExchageName(), rabbitProducerConfig.getRoutingKey(),
                pointVO);
    }

}
