package com.yk.logistic.controller.auction;

import com.yk.logistic.dto.auction.request.PlaceBidReqDto;
import com.yk.logistic.dto.auction.request.StartAuctionReqDto;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import com.yk.logistic.service.auction.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auctions")
public class AuctionApiController {

    private final AuctionService auctionService;

    // 경매 시작
    @PostMapping("/start")
    public ResponseEntity<AuctionResDto> startAuction(@RequestBody StartAuctionReqDto request) {
        AuctionResDto auction = auctionService.startAuction(
                request.getItemId(),
                request.getStartPrice(),
                request.getEndTime()
        );
        return ResponseEntity.ok(auction);
    }

    // 경매 종료
    @PostMapping("/{auctionId}/end")
    public ResponseEntity<AuctionResDto> endAuction(@PathVariable Long auctionId) {
        AuctionResDto auction = auctionService.endAuction(auctionId);
        return ResponseEntity.ok(auction);
    }
}