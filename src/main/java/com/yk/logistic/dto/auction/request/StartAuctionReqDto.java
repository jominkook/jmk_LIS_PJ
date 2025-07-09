package com.yk.logistic.dto.auction.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StartAuctionReqDto {
    private Long itemId; //경매대상 상품ID
    private int startPrice; //경매 시작가
    private LocalDateTime endTime; //경매 종료 시간
}
