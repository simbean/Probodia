package com.probodia.challengeservice.config.rabbitmq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probodia.challengeservice.messagequeue.entity.BSugarResponse;
import com.probodia.challengeservice.messagequeue.entity.MealDetailResponseVO;
import com.probodia.challengeservice.messagequeue.entity.MealResponseVO;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitListenerConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Jackson2JsonMessageConverter getConverter(
            @Autowired ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter messageConverter =
                new Jackson2JsonMessageConverter(objectMapper);
        messageConverter.setClassMapper(getClassMapper());
        return messageConverter;
    }


    @Bean
    public DefaultClassMapper getClassMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> map = new HashMap<>();
        map.put("com.probodia.userservice.api.vo.bsugar.BSugarResponse",
                BSugarResponse.class);
        map.put("com.probodia.userservice.api.vo.meal.MealResponseVO",
                MealResponseVO.class);
        map.put("com.probodia.userservice.api.vo.meal.MealDetailResponseVO",
                MealDetailResponseVO.class);

        classMapper.setIdClassMapping(map);
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

}