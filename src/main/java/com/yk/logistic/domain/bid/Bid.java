package com.yk.logistic.domain.bid;


import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 경매에 대한 입찰인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    // 입찰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    private Member bidder;

    // 입찰가
    @Column(nullable = false)
    private int bidPrice;

    // 입찰 시간
    @Column(nullable = false)
    private LocalDateTime bidTime = LocalDateTime.now();

    @Builder
    public Bid(Auction auction, Member bidder, int bidPrice) {
        this.auction = auction;
        this.bidder = bidder;
        this.bidPrice = bidPrice;
    }
}