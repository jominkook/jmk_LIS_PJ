package com.yk.logistic.domain.chat;

import java.time.LocalDateTime;

import com.yk.logistic.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅룸

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender; // 메시지 발신자

    private String message; // 메시지 내용
    private LocalDateTime timestamp = LocalDateTime.now(); // 메시지 전송 시간
    private boolean isRead = false; // 메시지 읽음 상태

    @Builder
    public ChatMessage(ChatRoom chatRoom, Member sender, String message) {
        if (chatRoom == null) {
            throw new IllegalArgumentException("ChatRoom cannot be null");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
    }

    // 메시지 읽음 상태 업데이트
    public void markAsRead() {
        this.isRead = true;
    }
    
    // senderId 반환 메서드 추가
    public Long getSenderId() {
        return sender != null ? sender.getId() : null;
    }
    
    
    

}