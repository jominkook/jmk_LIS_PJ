package com.yk.logistic.service.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;

@Component
public class ValidationCheck {

	//유효한 회원인지 검증
    public Member getMember(Optional<Member> member) {
        return member.orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다"));
    }
  

    //유효한 상품인지 검증
    public Item getItem(Optional<Item> item) {
        return item.orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }


}
