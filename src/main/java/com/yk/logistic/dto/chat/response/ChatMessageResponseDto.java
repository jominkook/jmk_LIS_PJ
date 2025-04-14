package com.yk.logistic.dto.chat.response;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.domain.member.Member;

@Getter
public class ChatMessageResponseDto {
    private Long id;
    private String senderName;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead; // 읽음 상태
    private LocalDateTime readAt; // 읽은 시간
    private List<String> readByUsers; // 메시지를 읽은 사용자 목록

    // ChatMessage 엔티티를 기반으로 DTO 생성
    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.senderName = chatMessage.getSender() != null ? chatMessage.getSender().getName() : "Unknown Sender";
        this.message = chatMessage.getMessage();
        this.timestamp = chatMessage.getTimestamp();
        this.isRead = !chatMessage.getReadByUsers().isEmpty(); // 읽은 사용자가 있으면 true
        this.readAt = chatMessage.getReadAt();
        this.readByUsers = chatMessage.getReadByUsers().stream()
            .map(Member::getName) // Member의 이름만 추출
            .collect(Collectors.toList());

        // 디버깅 로그
        System.out.println("ChatMessageResponseDto created: ID=" + this.id + ", isRead=" + this.isRead + ", readByUsers=" + this.readByUsers);
    }

    // 필요한 매개변수를 받는 생성자
    public ChatMessageResponseDto(Long id, String senderName, String message, LocalDateTime timestamp, boolean isRead, List<String> readByUsers) {
        this.id = id;
        this.senderName = senderName;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.readByUsers = readByUsers;
    }
}