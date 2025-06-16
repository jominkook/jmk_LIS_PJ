package com.yk.logistic.config.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 구독할 prefix 설정
        config.enableSimpleBroker("/topic"); // 메시지 브로커
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 때 사용할 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 설정
        registry.addEndpoint("/ws") // 클라이언트가 연결할 엔드포인트
        		.setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS 지원
    }
}
