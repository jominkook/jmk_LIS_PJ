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
import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.chat.request.ChatMessageRequestDto;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;
import com.yk.logistic.repository.chat.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    
    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable String chatRoomId, ChatMessageRequestDto chatMessageRequestDto) {
        System.out.println("chatRoomId: " + chatRoomId);
        System.out.println("Sender ID: " + chatMessageRequestDto.getSenderId());
        System.out.println("Message: " + chatMessageRequestDto.getMessage());

        // senderId를 기반으로 Member 객체 조회
        Member sender = memberRepository.findById(chatMessageRequestDto.getSenderId())
            .orElseThrow(() -> new IllegalArgumentException("발신자를 찾을 수 없습니다. ID: " + chatMessageRequestDto.getSenderId()));

        // ChatRoom 객체 생성 (필요 시 데이터베이스에서 조회)
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(chatRoomId))
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + chatRoomId));

        // ChatMessage 객체 생성
        ChatMessage chatMessage = ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(sender)
            .message(chatMessageRequestDto.getMessage())
            .build();

        // 메시지 저장
        ChatMessage savedMessage =chatMessageService.saveMessage(chatRoom.getId(), sender.getId(), chatMessage.getMessage());

        // 저장된 메시지를 기반으로 DTO 생성
        ChatMessageResponseDto responseDto = new ChatMessageResponseDto(savedMessage);
        //System.out.println("Sending WebSocket message: " + responseDto); // 로그로 확인
        return responseDto; // 메시지를 반환하여 클라이언트로 브로드캐스트
    }

    @GetMapping("/chat/{itemId}")
    public String getChatRoom(@PathVariable Long itemId, Model model, Authentication authentication) {
        // 현재 사용자 정보 가져오기
        String buyerEmail = authentication.getName();

        // 채팅방 생성 또는 조회
        Long chatRoomId = chatService.getOrCreateChatRoom(itemId, buyerEmail);

        System.out.println("Generated or Retrieved Chat Room ID: " + chatRoomId);
        System.out.println("Item ID: " + itemId);
        
        // 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 이메일: " + buyerEmail));
        
        
        Long senderId = member.getId(); // Member 객체에서 ID 가져오기
        String senderName = member.getName();  


        // 모델에 채팅방 ID 및 사용자 정보 추가
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("senderId", senderId);
        model.addAttribute("senderName", senderName);
        return "chatroom"; // chatroom.html 반환
    }
}