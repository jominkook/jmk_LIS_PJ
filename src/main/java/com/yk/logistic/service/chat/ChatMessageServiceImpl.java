package com.yk.logistic.service.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;
import com.yk.logistic.repository.chat.ChatMessageRepository;
import com.yk.logistic.repository.chat.ChatRoomRepository;
import com.yk.logistic.repository.member.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    
    

    @Override
    public ChatMessage saveMessage(Long chatRoomId, Long senderId, String message) {
    	
    	 System.out.println("Saving message...");
    	 System.out.println("chatRoomId: " + chatRoomId);
    	 System.out.println("senderId: " + senderId);
    	 System.out.println("message: " + message);
    	    
    	    
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅룸을 찾을 수 없습니다. ID: " + chatRoomId));
        Member sender = memberRepository.findById(senderId)
            .orElseThrow(() -> new IllegalArgumentException("발신자를 찾을 수 없습니다. ID: " + senderId));
        
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .message(message)
                .build();

        // 데이터베이스에 저장
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        //System.out.println("Saved Message ID: " + savedMessage.getId()); // 로그로 확인
        return savedMessage;
    }

    @Override
    public List<ChatMessageResponseDto> findMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅룸을 찾을 수 없습니다. ID: " + chatRoomId));

        List<ChatMessage> messages = chatMessageRepository.findByChatRoomOrderByTimestamp(chatRoom);

        // ChatMessage 엔티티를 ChatMessageResponseDto로 변환
        return messages.stream()
            .map(this::transform) // 
            .toList();
    }
    
    @Transactional
    @Override
    public ChatMessageResponseDto markAsRead(Long messageId, Long userId) {
    	
    	System.out.println("Starting markAsRead method...");
        System.out.println("messageId: " + messageId + ", userId: " + userId);
        
        // 메시지 조회
        ChatMessage message = chatMessageRepository.findById(messageId)
            .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다. ID: " + messageId));
        
        // 사용자 조회
        Member user = memberRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));
           
        // 메시지를 읽은 사용자 추가
        message.markAsReadByUser(user);
        
        // 메시지 저장
        chatMessageRepository.save(message);

        // 읽음 처리된 메시지를 DTO로 변환하여 반환
        return new ChatMessageResponseDto(message);
    }
    
    public Long getRecipientId(String chatRoomId, Long senderId) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(chatRoomId))
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + chatRoomId));

        // 채팅방의 판매자와 구매자 중 senderId가 아닌 사용자를 반환
        if (chatRoom.getSeller().getId().equals(senderId)) {
            return chatRoom.getBuyer().getId();
        } else {
            return chatRoom.getSeller().getId();
        }
    }

    // ChatMessage 엔티티를 DTO로 변환하는 transform 메서드
    private ChatMessageResponseDto transform(ChatMessage chatMessage) {
        return new ChatMessageResponseDto(
            chatMessage.getId(),
            chatMessage.getSender() != null ? chatMessage.getSender().getName() : "Unknown Sender",
            chatMessage.getSender() != null ? chatMessage.getSender().getId() : null, // senderId 추가
            chatMessage.getMessage(),
            chatMessage.getTimestamp(),
            !chatMessage.getReadByUsers().isEmpty(), // 읽음 상태: 읽은 사용자가 있으면 true
            chatMessage.getReadByUsers().stream()
                .map(Member::getName) // 읽은 사용자 이름 목록 추출
                .collect(Collectors.toList())
        );
    }
}