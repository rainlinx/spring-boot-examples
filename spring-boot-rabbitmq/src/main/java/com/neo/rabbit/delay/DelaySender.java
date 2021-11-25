package com.neo.rabbit.delay;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DelaySender {

    private final RabbitTemplate rabbitTemplate;

    public DelaySender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        rabbitTemplate.convertAndSend("delay", "delay message");
    }
}
