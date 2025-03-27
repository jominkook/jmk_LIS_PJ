package com.yk.logistic.service.chat;

import java.util.List;

import com.yk.logistic.domain.chat.ChatMessage;


public interface ChatMessageService {
    ChatMessage saveMessage(Long chatRoomId, Long senderId, String message);
    List<ChatMessage> findMessages(Long chatRoomId);
}