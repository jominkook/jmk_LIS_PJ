package com.yk.logistic.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.yk.logistic.domain.Item;
import com.yk.logistic.domain.Member;

@Component
public class ValidationCheck  {
	
	//회원 유호성 검증
	public Member getMember(Optional<Member> member) {
		return member.orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다"));
	}
	
	//중복회원 검사
	public void validateDuplicate(Optional<Member> member) {
		member.ifPresent(exist -> {
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		});
	}
	
	//유효한 상품인지 검증
    public Item getItem(Optional<Item> item) {
        return item.orElseThrow(() -> new IllegalArgumentException("없는 상품 입니다"));
    }



}
