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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    private Member bidder;

    @Column(nullable = false)
    private int bidPrice;

    @Column(nullable = false)
    private LocalDateTime bidTime = LocalDateTime.now();

    @Column(nullable = false)
    private boolean canceled = false; // 입찰 취소 여부

    @Builder
    public Bid(Auction auction, Member bidder, int bidPrice) {
        this.auction = auction;
        this.bidder = bidder;
        this.bidPrice = bidPrice;
        this.bidTime = LocalDateTime.now();
        this.canceled = false;
    }

    public void cancel() {
        this.canceled = true;
    }
}