package com.yk.logistic.repository.bid;

import java.util.List;

public interface BidRepositoryCustom {
    List<Long> findAuctionIdsByBidder(Long memberId);
}
