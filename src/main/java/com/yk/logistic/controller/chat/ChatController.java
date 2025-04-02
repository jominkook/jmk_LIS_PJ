package com.yk.logistic.controller.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.chat.ChatMessageService;
import com.yk.logistic.service.chat.ChatService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;

    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessage sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {
        System.out.println("chatRoomId: " + chatRoomId);
        System.out.println("Sender ID: " + chatMessage.getSenderId());
        System.out.println("Sender: " + chatMessage.getSender());
        System.out.println("Message: " + chatMessage.getMessage());

        try {
            chatMessageService.saveMessage(
                Long.parseLong(chatRoomId),
                chatMessage.getSenderId(),
                chatMessage.getMessage()
            );
        } catch (Exception e) {
            System.err.println("Error saving message: " + e.getMessage());
        }

        return chatMessage; // 메시지를 그대로 반환하여 클라이언트로 브로드캐스트
    }

    @GetMapping("/chat/{itemId}")
    public String getChatRoom(@PathVariable Long itemId, Model model, Authentication authentication) {
        // 현재 사용자 정보 가져오기
        String buyerEmail = authentication.getName();

        // 채팅방 생성 또는 조회
        Long chatRoomId = chatService.getOrCreateChatRoom(itemId, buyerEmail);

        System.out.println("Generated or Retrieved Chat Room ID: " + chatRoomId);
        System.out.println("Item ID: " + itemId);

        // 모델에 채팅방 ID 추가
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("senderId", memberRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 이메일: " + buyerEmail)).getId());
        //model.addAttribute("senderName", senderName);
        return "chatroom"; // chatroom.html 반환
    }
}