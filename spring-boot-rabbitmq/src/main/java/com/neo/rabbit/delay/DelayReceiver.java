package com.neo.rabbit.delay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelayReceiver {

    @RabbitListener(queues = "dead")
    public void process(Message message) {
        String msg = new String(message.getBody());
        log.info("Delay Receiver : " + msg);
    }
}
