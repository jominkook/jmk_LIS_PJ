package com.yk.logistic.domain.auction;

import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // 경매 대상 상품

    @Column(nullable = false)
    private int startPrice; // 경매 시작가

    @Column(nullable = false)
    private int currentPrice; // 현재 최고가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Member winner; // 현재 최고 입찰자

    @Column(nullable = false)
    private LocalDateTime startTime; // 경매 시작 시간

    @Column(nullable = false)
    private LocalDateTime endTime; // 경매 종료 시간

    @Enumerated(EnumType.STRING)
    private AuctionStatus status; // 경매 상태

    // 생성자 및 빌더
    @Builder
    public Auction(Item item, int startPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.item = item;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = AuctionStatus.ACTIVE; // 기본 상태를 ACTIVE로 설정
        this.currentPrice = startPrice; // 현재가는 시작가로 초기화

        // 경매 시작 시 아이템 상태도 ACTIVE로 변경
        if (item != null) {
            item.startAuction();
        }
    }

    // 경매 취소
    public void cancelAuction() {
        if (this.status != AuctionStatus.ACTIVE) {
            throw new IllegalStateException("진행 중인 경매만 취소할 수 있습니다.");
        }
        this.status = AuctionStatus.CANCELLED;
        this.startPrice = 0;
        this.currentPrice = 0;
        this.winner = null;
        this.endTime = LocalDateTime.now(); // endTime 초기화
    }

    // 경매 종료
    public void endAuction() {
        if (this.status != AuctionStatus.ACTIVE) {
            throw new IllegalStateException("경매가 이미 종료되었거나 활성화되지 않았습니다.");
        }
        this.status = AuctionStatus.ENDED;
    }

    // 도메인 메서드: 입찰 처리
    public void placeBid(int bidPrice, Member bidder) {
        if (bidPrice <= currentPrice) {
            throw new IllegalArgumentException("입찰가는 현재가보다 높아야 합니다.");
        }
        if (!isAuctionActive()) {
            throw new IllegalStateException("경매가 활성화 상태가 아닙니다.");
        }
        this.currentPrice = bidPrice;
        this.winner = bidder;
    }

    // 도메인 메서드: 경매 활성 상태 확인
    public boolean isAuctionActive() {
        return LocalDateTime.now().isBefore(endTime) && status == AuctionStatus.ACTIVE;
    }
}