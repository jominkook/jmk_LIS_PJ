package com.yk.logistic.service.chat;

import java.util.List;

import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;


public interface ChatMessageService {
    ChatMessage saveMessage(Long chatRoomId, Long senderId, String message);
    List<ChatMessageResponseDto> findMessages(Long chatRoomId);
    ChatMessageResponseDto markAsRead(Long messageId, Long userId);
    public Long getRecipientId(String chatRoomId, Long senderId);
}