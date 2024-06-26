package com.rainlin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // springboot2.5后跨域的配置变更为setAllowedOriginPatterns
        registry.addEndpoint("/spring-boot-websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 基于内存消息代理
        // 同时开启会导致重复消费消息
        //registry.enableSimpleBroker("/topic", "/queue", "/user");

        // 基于rabbitmq-stomp消息代理
        registry.enableStompBrokerRelay("/topic", "/queue", "/amq/queue", "/exchange");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
