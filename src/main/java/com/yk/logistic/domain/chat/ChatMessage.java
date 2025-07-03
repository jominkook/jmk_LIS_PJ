package com.yk.logistic.domain.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
        name = "message_read_users",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )


    private List<Member> readByUsers = new ArrayList<>(); // 메시지를 읽은 사용자 목록

    @Column(name = "read_at")
    private LocalDateTime readAt; // 메시지 읽은 시간

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

    // 메시지를 읽은 사용자 추가
    public void markAsReadByUser(Member user) {
        if (!readByUsers.contains(user)) {
            readByUsers.add(user);
            this.readAt = LocalDateTime.now(); // 읽은 시간 업데이트
        }
    }

    // senderId 반환 메서드 추가
    public Long getSenderId() {
        return sender != null ? sender.getId() : null;
    }
}