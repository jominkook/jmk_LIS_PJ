package com.yk.logistic.domain.chat;

import java.time.LocalDateTime;

import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender; // 메시지 발신자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver; // 메시지 수신자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 관련 상품

    private String message; // 메시지 내용
    private LocalDateTime timestamp; // 메시지 전송 시간
    private boolean isRead; // 메시지 읽음 상태

    @Builder
    public Chat(Member sender, Member receiver, Item item, String message, LocalDateTime timestamp, boolean isRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }
}