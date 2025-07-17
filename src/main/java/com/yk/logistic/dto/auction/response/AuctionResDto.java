package com.yk.logistic.dto.auction.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuctionResDto {
    private Long auctionId; // 경매 ID
    private Long itemId; // 경매 대상 상품 ID
    private Long startPrice; // 경매 시작가
    private Long currentPrice; // 현재 최고가
    private String winnerName; // 현재 최고 입찰자 이름
    private LocalDateTime startTime; // 경매 시작 시간
    private LocalDateTime endTime; // 경매 종료 시간
    private String status; // 경매 상태

    public AuctionResDto(Long auctionId, Long itemId, Long startPrice, Long currentPrice, String winnerName,
                         LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.winnerName = winnerName != null ? winnerName : null; // 기본값 설정
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status != null ? status : null; // 기본값 설정
    }
}