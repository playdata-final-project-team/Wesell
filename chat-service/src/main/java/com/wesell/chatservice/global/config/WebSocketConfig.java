package com.wesell.chatservice.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 소켓 연결과 관련된 설정
     *
     * addEndpoint : 소켓 연결 URL
     * setAllowedOriginPatterns : 소켓 CORS 설정
     * withSocketJS : 소켓을 지원하지 않는 브라우저라면 socketJs를 사용하도록 설정.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*");
    }

    /**
     * STOMP 사용을 위한 MessageBroker 설정
     *
     * enableSimpleBroker : 서버에서 구독자로 발행하는 메시지에 대한 end-point 설정
     * /sub 로 시작하는 경로를 구독하는 클라이언트들에게 서버가 메시지를 발행한다.
     * ex) /sub/chat/room/1 구독 시, 서버가 해당 경로로 메시지를 발행하면, 해당 메시지는 /sub/chat/room/1을 구독하는
     * 모든 클라이언트로 메시지가 전달된다.
     *
     * setApplicationMDestinationPrefixes : 발행자가 메시지를 보낼 end-point 설정
     * 클라이언트가 메시지를 발행 시 경로 앞에 /pub 가 붙은 경우, 메시지 브로커로 전달된다.
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
