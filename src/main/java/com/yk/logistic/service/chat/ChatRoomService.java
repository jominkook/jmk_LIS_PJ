package com.yk.logistic.service.chat;

import com.yk.logistic.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom createChatRoom(Long sellerId, Long buyerId, Long itemId);
    List<ChatRoom> findChatRooms(Long memberId);
}