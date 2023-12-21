package com.wesell.dealservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("DEAL_CREATE_QUEUE", message);

    }

    public void updateMessage(String message) {
        rabbitTemplate.convertAndSend("DEAL_UPDATE_QUEUE", message);
    }


}
