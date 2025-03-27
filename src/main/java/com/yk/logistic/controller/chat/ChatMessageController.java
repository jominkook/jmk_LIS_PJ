package com.yk.logistic.controller.chat;

import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.dto.chat.request.ChatMessageRequestDto;
import com.yk.logistic.dto.chat.response.ChatMessageResponseDto;
import com.yk.logistic.service.chat.ChatMessageService;
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
    public ResponseEntity<List<ChatMessageResponseDto>> getMessages(@PathVariable Long chatRoomId) {
        List<ChatMessage> messages = chatMessageService.findMessages(chatRoomId);
        List<ChatMessageResponseDto> responseDtos = messages.stream()
            .map(ChatMessageResponseDto::new)
            .toList();
        return ResponseEntity.ok(responseDtos);
    }

    // 메시지 생성 (전송)
    @PostMapping
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto requestDto) {
        ChatMessage chatMessage = chatMessageService.saveMessage(
            requestDto.getChatRoomId(),
            requestDto.getSenderId(),
            requestDto.getMessage()
        );
        ChatMessageResponseDto responseDto = new ChatMessageResponseDto(chatMessage);
        return ResponseEntity.ok(responseDto);
    }
}