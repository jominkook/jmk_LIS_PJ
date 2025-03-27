package com.yk.logistic.service.chat;

import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.chat.ChatRoomRepository;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Override
    public ChatRoom createChatRoom(Long sellerId, Long buyerId, Long itemId) {
        Member seller = memberRepository.findById(sellerId)
            .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다. ID: " + sellerId));
        Member buyer = memberRepository.findById(buyerId)
            .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다. ID: " + buyerId));
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));

        return chatRoomRepository.save(ChatRoom.builder()
            .seller(seller)
            .buyer(buyer)
            .item(item)
            .build());
    }

    @Override
    public List<ChatRoom> findChatRooms(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + memberId));
        return chatRoomRepository.findBySellerOrBuyer(member, member);
    }
}