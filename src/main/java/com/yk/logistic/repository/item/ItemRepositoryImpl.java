package com.yk.logistic.repository.item;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yk.logistic.domain.item.QItem;
import com.yk.logistic.domain.item.QItemImage;
import com.yk.logistic.domain.category.QCategory;
import com.yk.logistic.domain.member.QMember;
import com.yk.logistic.domain.auction.QAuction;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemResDto> findAuctionItems() {
        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;
        QAuction auction = QAuction.auction;
        QMember seller = QMember.member;
        QCategory category = QCategory.category;
        QCategory parentCategory = new QCategory("parentCategory");

        return queryFactory
                .select(Projections.constructor(
                        ItemResDto.class,
                        item.id,
                        item.title,
                        item.origin,
                        item.price,
                        seller.id,
                        seller.name,
                        seller.email,
                        category.name,
                        parentCategory.name,
                        itemImage.imageUrl, // 대표 이미지 URL
                        auction.startPrice,
                        auction.endTime.stringValue(),
                        item.description,
                        // category.fullName은 제거
                        item.latitude,
                        item.longitude,
                        auction.status.stringValue(),
                        auction.id,
                        Projections.constructor(AuctionResDto.class,
                                auction.id,
                                auction.item.id,
                                auction.startPrice,
                                auction.currentPrice,
                                auction.winner.name,
                                auction.startTime,
                                auction.endTime,
                                auction.status.stringValue()
                        )
                ))
                .from(item)
                .leftJoin(item.images, itemImage)
                .join(item.auction, auction)
                .join(item.seller, seller)
                .join(item.category, category)
                .leftJoin(category.parent, parentCategory)
                .fetch();
    }
}