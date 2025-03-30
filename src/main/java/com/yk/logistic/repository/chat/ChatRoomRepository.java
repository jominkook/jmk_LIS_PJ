package com.yk.logistic.repository.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	// 특정 상품과 사용자 간의 채팅룸 조회
	Optional<ChatRoom> findChatRoomBySellerAndBuyerAndItemId(@Param("seller") Member seller, @Param("buyer") Member buyer, @Param("itemId") Long itemId);

    // 사용자가 참여 중인 채팅룸 목록 조회
    List<ChatRoom> findBySellerOrBuyer(Member seller, Member buyer);

}
