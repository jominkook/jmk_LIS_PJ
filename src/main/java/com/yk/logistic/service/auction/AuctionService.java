package com.yk.logistic.service.auction;

import com.yk.logistic.dto.auction.response.AuctionResDto;

import java.time.LocalDateTime;

public interface AuctionService {
    // 경매 시작
    AuctionResDto startAuction(Long itemId, Long startPrice, LocalDateTime endTime);

    // 입찰
    AuctionResDto placeBid(Long auctionId, Long bidPrice, Long bidderId);

    //경매 취소
    AuctionResDto cancelAuction(Long auctionId);

    // 경매 종료
    AuctionResDto endAuction(Long auctionId);
}