package io.ruslan.top100crypto.config;

import io.ruslan.top100crypto.websocket.StompClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {

    @Bean
    public StompClient setupStompClient() {
        return new StompClient();
    }
}