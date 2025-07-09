package com.yk.logistic.controller.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.chat.request.ChatMessageRequestDto;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;
import com.yk.logistic.repository.chat.ChatRoomRepository;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.chat.ChatMessageService;
import com.yk.logistic.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final NotificationService notificationService;
    
    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable String chatRoomId, ChatMessageRequestDto chatMessageRequestDto) {
//        System.out.println("chatRoomId: " + chatRoomId);
//        System.out.println("Sender ID: " + chatMessageRequestDto.getSenderId());
//        System.out.println("Message: " + chatMessageRequestDto.getMessage());

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
        ChatMessage savedMessage = chatMessageService.saveMessage(chatRoom.getId(), sender.getId(), chatMessage.getMessage());

        // 저장된 메시지를 기반으로 DTO 생성
        ChatMessageResponseDto responseDto = new ChatMessageResponseDto(savedMessage);
        //System.out.println("Sending WebSocket message: " + responseDto); // 로그로 확인
        
        // 알림 전송 (판매자에게)
        Long recipientId = chatMessageService.getRecipientId(chatRoomId, chatMessageRequestDto.getSenderId());
        notificationService.sendNotification(recipientId, "새로운 채팅 메시지가 도착했습니다!");
        
        
        return responseDto; // 메시지를 반환하여 클라이언트로 브로드캐스트
    }
}
