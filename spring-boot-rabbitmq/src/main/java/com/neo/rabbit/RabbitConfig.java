package com.neo.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class RabbitConfig {

    private final RabbitTemplate rabbitTemplate;

    public RabbitConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 设置消息的回调函数，需在配置文件中开启相应配置后生效
     * ConfirmCallback：消息成功发送至exchange后的回调函数
     * ReturnCallback：消息未成功发送至queue后的回调函数
     */
    @PostConstruct
    public void init() {
        this.rabbitTemplate.setConfirmCallback(confirmCallback());
        this.rabbitTemplate.setReturnCallback(returnCallback());
    }

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return (correlationData, ack, cause) -> log.info("消息传递到了exchange");
    }

    @Bean
    public RabbitTemplate.ReturnCallback returnCallback() {
        return (message, replyCode, replyText, exchange, routingKey) -> log.info("消息未传递到queue");
    }
}
