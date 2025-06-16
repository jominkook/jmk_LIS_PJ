package com.yk.logistic.controller.chat;

import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.chat.response.ChatRoomResponseDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.chat.ChatRoomService;
import com.yk.logistic.service.item.ItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

    // 채팅룸 생성 또는 연결
    @PostMapping("/connect")
    public ResponseEntity<ChatRoomResponseDto> connectChatRoom(@RequestParam Long itemId) {
        // 현재 로그인된 사용자 (구매자) 정보 가져오기
        Member buyer = getAuthenticatedMember();

        // 아이템의 판매자 정보 가져오기 (ItemService를 활용)
        ItemResDto item = itemService.findItem(itemId); // ItemResDto 반환
        Long sellerId = item.getSellerId(); // 판매자 ID 가져오기

        // 채팅방 생성 또는 기존 채팅방 반환
        ChatRoom chatRoom = chatRoomService.createChatRoom(
            sellerId,          // 판매자 ID
            buyer.getId(),     // 구매자 ID
            itemId             // 아이템 ID
        );

        return ResponseEntity.ok(new ChatRoomResponseDto(chatRoom));
    }

    // 사용자가 참여 중인 채팅룸 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms() {
        // 현재 로그인된 사용자 정보 가져오기
        Member member = getAuthenticatedMember();

        // 사용자가 참여 중인 채팅룸 목록 조회
        List<ChatRoom> chatRooms = chatRoomService.findChatRooms(member.getId());
        List<ChatRoomResponseDto> responseDtos = chatRooms.stream()
            .map(ChatRoomResponseDto::new)
            .toList();

        return ResponseEntity.ok(responseDtos);
    }
    
    // 인증된 사용자 정보를 가져오는 메서드
    private Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 인증된 사용자의 이메일
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("인증된 사용자를 찾을 수 없습니다."));
    }
}