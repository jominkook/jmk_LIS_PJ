package com.yk.logistic.service.auction;

import com.yk.logistic.dto.auction.response.AuctionResDto;

import java.time.LocalDateTime;

public interface AuctionService {
    // 경매 시작
    AuctionResDto startAuction(Long itemId, int startPrice, LocalDateTime endTime);

    // 입찰
    AuctionResDto placeBid(Long auctionId, int bidPrice, Long bidderId);

    // 경매 종료
    AuctionResDto endAuction(Long auctionId);
}