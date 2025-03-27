package com.yk.logistic.dto.chat.response;

import com.yk.logistic.domain.chat.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long id;
    private String sellerName;
    private String buyerName;
    private String itemName;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.sellerName = chatRoom.getSeller().getName();
        this.buyerName = chatRoom.getBuyer().getName();
        this.itemName = chatRoom.getItem().getTitle();
    }
}