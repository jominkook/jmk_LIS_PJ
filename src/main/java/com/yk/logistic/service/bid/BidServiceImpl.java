package com.yk.logistic.service.bid;

import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.bid.Bid;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import com.yk.logistic.repository.auction.AuctionRepository;
import com.yk.logistic.repository.bid.BidRepository;
import com.yk.logistic.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;

    @Override
    public AuctionResDto placeBid(Long auctionId, Long bidPrice, Long bidderId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매 없음"));
        Member bidder = memberRepository.findById(bidderId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        auction.placeBid(bidPrice, bidder); // 도메인 메서드 활용

        bidRepository.save(Bid.builder()
                .auction(auction)
                .bidder(bidder)
                .bidPrice(bidPrice)
                .build());

        return transFormDomain(auction); // 도메인 → DTO 변환
    }

    @Override
    public List<Long> findAuctionIdsByBidder(Long memberId) {
        return bidRepository.findAuctionIdsByBidder(memberId);
    }

    @Transactional
    public AuctionResDto changeBid(Long auctionId, String email, Long bidPrice) {
        Bid bid = bidRepository.findByAuctionIdAndBidderEmail(auctionId, email)
                .orElseThrow(() -> new IllegalArgumentException("입찰 내역이 없습니다."));
        bid.changeBidPrice(bidPrice); // setter 대신 변경 메서드 사용 권장
        bidRepository.save(bid);

        Auction auction = bid.getAuction(); // getter로 참조
        return transFormDomain(auction);
    }

    private AuctionResDto transFormDomain(Auction auction) {
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
