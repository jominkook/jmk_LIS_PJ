package com.yk.logistic.controller.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.yk.logistic.domain.chat.ChatMessage;

import ch.qos.logback.core.model.Model;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage") // 클라이언트가 "/app/chat.sendMessage"로 보낸 메시지를 처리
    @SendTo("/topic/public") // 메시지를 "/topic/public"을 구독한 클라이언트에게 전송
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage; // 메시지를 그대로 반환
    }
    
    @GetMapping("/chat")
    public String chatPage(Model model) {
        // 필요한 데이터를 모델에 추가할 수 있습니다.
        return "chatroom"; // chatroom.html 템플릿을 반환
    }
}
