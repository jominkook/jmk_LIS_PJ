package com.yk.logistic.controller.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.service.chat.ChatService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;
	
	@MessageMapping("/{chatRoomId}/sendMessage")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessage sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {
		
		System.out.println(chatMessage.getSender());
        // 메시지 처리 로직 (예: DB 저장)
        System.out.println("Received message: " + chatMessage.getMessage() + " from chatRoomId: " + chatRoomId);
        return chatMessage; // 메시지를 그대로 반환하여 클라이언트로 브로드캐스트
    }
    
    @GetMapping("/{itemId}")
    public String getChatRoom(@PathVariable Long itemId, Model model, Authentication authentication) {
        // 현재 사용자 정보 가져오기
        String buyerEmail = authentication.getName();

        // 채팅방 생성 또는 조회
        Long chatRoomId = chatService.getOrCreateChatRoom(itemId, buyerEmail);

        // 모델에 채팅방 ID 추가
        model.addAttribute("chatRoomId", chatRoomId);
        return "chatroom"; // chatroom.html 반환
    }
    
}
