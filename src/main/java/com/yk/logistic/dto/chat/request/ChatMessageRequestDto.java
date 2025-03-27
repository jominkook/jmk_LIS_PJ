package com.yk.logistic.dto.chat.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {
    private Long chatRoomId; // 채팅룸 ID
    private Long senderId;   // 발신자 ID
    private String message;  // 메시지 내용
}