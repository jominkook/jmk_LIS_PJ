package com.yk.logistic.controller.chat;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.chat.ChatService;
import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
@RequestMapping("/view")
public class ChatViewController {
	private final ChatService chatService;
    private final MemberRepository memberRepository;
	
	@GetMapping("/chat/{itemId}")
    public String getChatRoom(@PathVariable Long itemId, Model model, Authentication authentication) {
        // 현재 사용자 정보 가져오기
        String buyerEmail = authentication.getName();

        // 채팅방 생성 또는 조회
        Long chatRoomId = chatService.getOrCreateChatRoom(itemId, buyerEmail);

        System.out.println("Generated or Retrieved Chat Room ID: " + chatRoomId);
        System.out.println("Item ID: " + itemId);
        
        // 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 이메일: " + buyerEmail));
        
        
        Long senderId = member.getId(); // Member 객체에서 ID 가져오기
        String senderName = member.getName();  


        // 모델에 채팅방 ID 및 사용자 정보 추가
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("senderId", senderId);
        model.addAttribute("senderName", senderName);
        return "chatroom"; // chatroom.html 반환
    }

}
