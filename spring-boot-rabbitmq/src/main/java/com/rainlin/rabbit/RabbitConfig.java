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
     * <ul>
     *     <li>设置消息转换器为JSON</li>
     *     <li>设置消息的回调函数</li>
     * </ul>
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

    /**
     * 必须要注册为bean，否则@RabbitListener不会使用该消息转换器
     *
     * @return Jackson2JsonMessageConverter
     */
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

    /**
     * 消息成功发送至exchange后的回调函数，同时需配置{@code spring.rabbitmq.publisher-confirms: true}
     *
     * @return RabbitTemplate.ConfirmCallback
     */
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

    /**
     * 消息未成功发送至queue后的回调函数，同时需配置{@code spring.rabbitmq.publisher-returns: true}
     *
     * @return RabbitTemplate.ReturnCallback
     */
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
