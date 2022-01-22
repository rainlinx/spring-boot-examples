package com.rainlin.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 *
 * @author rainlin
 */
@Slf4j
@Configuration
public class RabbitConfig {

    /**
     * 自定义RabbitTemplate
     * 1.设置消息转换器为JSON
     * 2.设置消息的回调函数，需在配置文件中开启相应配置后生效
     * ConfirmCallback：消息成功发送至exchange后的回调函数
     * ReturnCallback：消息未成功发送至queue后的回调函数
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnCallback(returnCallback());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
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
        return (correlationData, ack, cause) -> {
            log.info("=======ConfirmCallback=========");
            log.info("correlationData = " + correlationData);
            log.info("ack = " + ack);
            log.info("cause = " + cause);
            log.info("=======ConfirmCallback=========");
        };
    }

    @Bean
    public RabbitTemplate.ReturnCallback returnCallback() {
        return (message, replyCode, replyText, exchange, routingKey) -> {
            log.info("--------------ReturnCallback----------------");
            log.info("message = " + message);
            log.info("replyCode = " + replyCode);
            log.info("replyText = " + replyText);
            log.info("exchange = " + exchange);
            log.info("routingKey = " + routingKey);
            log.info("--------------ReturnCallback----------------");
        };
    }
}
