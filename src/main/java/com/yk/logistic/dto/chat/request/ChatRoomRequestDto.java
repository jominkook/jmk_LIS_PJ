package com.yk.logistic.dto.chat.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomRequestDto {
    private Long sellerId; // 판매자 ID
    private Long buyerId;  // 구매자 ID
    private Long itemId;   // 상품 ID
}