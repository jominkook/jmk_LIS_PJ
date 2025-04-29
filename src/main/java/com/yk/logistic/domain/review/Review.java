package com.yk.logistic.domain.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Item item;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int rating;

    @Builder
    public Review(Member reviewee, Item item, String content, int rating) {
        this.reviewee = reviewee;
        this.item = item;
        this.content = content;
        this.rating = rating;
    }
}