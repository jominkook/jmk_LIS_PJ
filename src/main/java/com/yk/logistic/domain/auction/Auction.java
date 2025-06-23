package com.yk.logistic.domain.auction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double startPrice;

    private Double currentPrice;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

//    private Long sellerId;
//    private Long winnerId;

//    @Column(length = 20)
//    private String status; // 예: "OPEN", "CLOSED"
}
