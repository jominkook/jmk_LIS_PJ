package com.yk.logistic.service.auction;

import java.time.LocalDateTime;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.auction.AuctionStatus;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import com.yk.logistic.repository.auction.AuctionRepository;
import com.yk.logistic.repository.item.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    public AuctionResDto startAuction(Long itemId, Long startPrice, LocalDateTime endTime) {
        // 아이템 조회 및 검증
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        if (item.getAuction() != null) {
            throw new IllegalStateException("이미 경매가 시작된 아이템입니다.");
        }

        // Auction 객체 생성 (빌더 사용)
        Auction newAuction = Auction.builder()
                .item(item)
                .startPrice(startPrice)
                .startTime(LocalDateTime.now())
                .endTime(endTime)
                .build();

        // 아이템에 경매 설정
        item.setAuction(newAuction); // 도메인 메서드 활용

        // 경매 저장
        Auction savedAuction = auctionRepository.save(newAuction);

        return transformToDto(savedAuction);
    }

    @Override
    public AuctionResDto placeBid(Long auctionId, Long bidPrice, Long bidderId) {
        // 경매 조회 및 검증
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        // 입찰자 조회
        Member bidder = memberRepository.findById(bidderId)
                .orElseThrow(() -> new IllegalArgumentException("입찰자를 찾을 수 없습니다."));

        // 입찰 처리
        auction.placeBid(bidPrice, bidder);

        return transformToDto(auction);
    }

    @Override
    public AuctionResDto cancelAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        auction.cancelAuction(); // 경매 취소 처리 (엔티티에 메서드 필요)

        return transformToDto(auction);
    }

    @Override
    public AuctionResDto endAuction(Long auctionId) {
        // 경매 조회 및 검증
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        // 경매 종료 처리
        auction.endAuction();

        return transformToDto(auction);
    }

    private AuctionResDto transformToDto(Auction auction) {
        return new AuctionResDto(
                auction.getId(),
                auction.getItem().getId(),
                auction.getStartPrice(),
                auction.getCurrentPrice(),
                auction.getWinner() != null ? auction.getWinner().getName() : null,
                auction.getStartTime(),
                auction.getEndTime(),
                auction.getStatus().name()
        );
    }
}