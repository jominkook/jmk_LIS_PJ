package com.yk.logistic.dto.auction.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceBidReqDto {
    private int bidPrice;
    private Long bidderId;
}
