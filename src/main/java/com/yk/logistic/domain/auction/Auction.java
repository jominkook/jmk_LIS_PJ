package com.yk.logistic.domain.auction;

import com.yk.logistic.domain.bid.Bid;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //경매 대상 상품(1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    //경매시작가
    @Column(nullable = false)
    private int startPrice;
    // 현재 최고가
    @Column(nullable = false)
    private int currentPrice;

    // 현재 최고 입찰자(낙찰 후보)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Member winner;

    // 경매 시작/종료 시간
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;


    // 입찰 내역
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    @Builder
    public Auction(Item item, int startPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.item = item;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 최고가 및 낙찰 후보 갱신
    public void updateCurrentPrice(int price, Member bidder) {
        this.currentPrice = price;
        this.winner = bidder;
    }


}
