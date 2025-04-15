package com.yk.logistic.controller.chat;

import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.dto.chat.request.ChatMessageRequestDto;
import com.yk.logistic.dto.chat.request.MarkMessageReadRequestDto;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;
import com.yk.logistic.service.chat.ChatMessageService;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    // 특정 채팅룸의 메시지 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessages(@PathVariable Long chatRoomId,Model model) {
        List<ChatMessageResponseDto> responseDtos = chatMessageService.findMessages(chatRoomId);
        return ResponseEntity.ok(responseDtos);
    }

    // 메시지 생성 (전송)
    @PostMapping
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto requestDto) {
        // 메시지 저장
        ChatMessage chatMessage = chatMessageService.saveMessage(
            requestDto.getChatRoomId(),
            requestDto.getSenderId(),
            requestDto.getMessage()
        );

        // 저장된 메시지를 DTO로 변환하여 반환
        ChatMessageResponseDto responseDto = new ChatMessageResponseDto(chatMessage);
        return ResponseEntity.ok(responseDto);
    }
    
    // 메시지 읽음 처리
    @PatchMapping("/{messageId}/read")
    public ResponseEntity<ChatMessageResponseDto> markMessageAsRead(
            @PathVariable Long messageId,
            @RequestBody MarkMessageReadRequestDto requestDto) {
        Long userId = requestDto.getUserId();
        //System.out.println("Marking message as read: " + messageId + " by user: " + userId);
        ChatMessageResponseDto responseDto = chatMessageService.markAsRead(messageId, userId);
        //System.out.println("Message marked as read: " + responseDto);
        return ResponseEntity.ok(responseDto);
    }
}