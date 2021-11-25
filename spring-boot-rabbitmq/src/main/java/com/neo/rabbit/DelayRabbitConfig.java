package com.neo.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayRabbitConfig {

    @Bean
    public Queue delayQueue() {
        return QueueBuilder
                .durable("delay")
                .withArgument("x-dead-letter-exchange", "dead")
                .withArgument("x-dead-letter-routing-key", "dead")
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable("dead").build();
    }

    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange("dead").build();
    }

    @Bean
    public Binding bindingDeadExchange(Exchange deadExchange, Queue deadQueue) {
        return BindingBuilder.bind(deadQueue).to(((DirectExchange) deadExchange)).with("dead");
    }
}
