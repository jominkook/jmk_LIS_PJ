package com.yk.logistic.controller.chat;

import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.dto.chat.request.ChatRoomRequestDto;
import com.yk.logistic.dto.chat.response.ChatRoomResponseDto;
import com.yk.logistic.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 채팅룸 생성
    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(
            requestDto.getSellerId(),
            requestDto.getBuyerId(),
            requestDto.getItemId()
        );
        ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자가 참여 중인 채팅룸 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms(@RequestParam Long memberId) {
        List<ChatRoom> chatRooms = chatRoomService.findChatRooms(memberId);
        List<ChatRoomResponseDto> responseDtos = chatRooms.stream()
            .map(ChatRoomResponseDto::new)
            .toList();
        return ResponseEntity.ok(responseDtos);
    }
}