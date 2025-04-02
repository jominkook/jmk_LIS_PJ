package com.yk.logistic.service.chat;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.chat.ChatRoomRepository;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    
 
	public Long getOrCreateChatRoom(Long itemId, String buyerEmail) {
		// 판매자와 구매자 정보 가져오기
	    Item item = itemRepository.findById(itemId)
	            .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다. ID: " + itemId));
	    Member seller = item.getSeller();
	    Member buyer = memberRepository.findByEmail(buyerEmail)
	            .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다. 이메일: " + buyerEmail));

	    // 기존 채팅방 조회 (아이템 ID만으로 조회)
	    Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByItemId(itemId);
	    if (existingChatRoom.isPresent()) {
	        return existingChatRoom.get().getId();
	    }

        // 채팅방 생성
        ChatRoom newChatRoom = ChatRoom.builder()
                .seller(seller)
                .buyer(buyer)
                .item(item)
                .build();
        return chatRoomRepository.save(newChatRoom).getId();
    }
}
