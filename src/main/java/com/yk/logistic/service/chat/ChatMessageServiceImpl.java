package com.yk.logistic.service.chat;

import java.util.List;
import org.springframework.stereotype.Service;
import com.yk.logistic.domain.chat.ChatMessage;
import com.yk.logistic.domain.chat.ChatRoom;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.chat.ChatMessageRepository;
import com.yk.logistic.repository.chat.ChatRoomRepository;
import com.yk.logistic.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Override
    public ChatMessage saveMessage(Long chatRoomId, Long senderId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅룸을 찾을 수 없습니다. ID: " + chatRoomId));
        Member sender = memberRepository.findById(senderId)
            .orElseThrow(() -> new IllegalArgumentException("발신자를 찾을 수 없습니다. ID: " + senderId));

        return chatMessageRepository.save(ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(sender)
            .message(message)
            .build());
    }

    @Override
    public List<ChatMessage> findMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅룸을 찾을 수 없습니다. ID: " + chatRoomId));
        return chatMessageRepository.findByChatRoomOrderByTimestamp(chatRoom);
    }
}