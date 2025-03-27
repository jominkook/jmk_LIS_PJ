package com.yk.logistic.dto.chat.response;

import com.yk.logistic.domain.chat.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResponseDto {
    private Long id;
    private String senderName;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.senderName = chatMessage.getSender().getName();
        this.message = chatMessage.getMessage();
        this.timestamp = chatMessage.getTimestamp();
        this.isRead = chatMessage.isRead();
    }
}