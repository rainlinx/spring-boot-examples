package com.rainlin.config;

import com.rainlin.websocket.MarcoHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MarcoHandler marcoHandler;

    public WebSocketConfig(MarcoHandler marcoHandler) {
        this.marcoHandler = marcoHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(marcoHandler, "/marco").setAllowedOrigins("*");
    }
}
