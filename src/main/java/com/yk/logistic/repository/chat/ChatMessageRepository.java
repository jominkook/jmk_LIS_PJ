package com.yk.logistic.repository.chat;

import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 채팅룸의 메시지 조회
    List<ChatMessage> findByChatRoomOrderByTimestamp(ChatRoom chatRoom);
}