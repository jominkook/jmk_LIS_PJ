package com.yk.logistic.service.bid;

import com.yk.logistic.dto.auction.response.AuctionResDto;

import java.util.List;

public interface BidService {
    AuctionResDto placeBid(Long auctionId, Long bidPrice, Long bidderId);

    List<Long> findAuctionIdsByBidder(Long memberId);

    AuctionResDto changeBid(Long auctionId, String email, Long bidPrice);
}
