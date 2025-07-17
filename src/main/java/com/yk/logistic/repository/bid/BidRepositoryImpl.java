package com.yk.logistic.repository.bid;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yk.logistic.domain.bid.QBid;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BidRepositoryImpl implements BidRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Long> findAuctionIdsByBidder(Long memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QBid bid = QBid.bid;
        return queryFactory
                .select(bid.auction.id)
                .distinct()
                .from(bid)
                .where(bid.bidder.id.eq(memberId))
                .fetch();
    }
}
