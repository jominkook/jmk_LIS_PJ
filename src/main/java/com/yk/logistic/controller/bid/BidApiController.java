package com.yk.logistic.controller.bid;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.bid.request.PlaceBidReqDto;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.bid.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidApiController {
    private final BidService bidService;

    private final MemberRepository memberRepository;

    @PostMapping("/api/auctions/{auctionId}/bid")
    public ResponseEntity<?> placeBid(@PathVariable Long auctionId,
                                      @RequestBody PlaceBidReqDto dto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // 기본 UserDetails는 username이 이메일일 수 있음
        // email로 Member를 조회해서 id를 얻은 뒤 입찰 처리
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));
        bidService.placeBid(auctionId, dto.getBidPrice(), member.getId());
        return ResponseEntity.ok().build();
    }
}