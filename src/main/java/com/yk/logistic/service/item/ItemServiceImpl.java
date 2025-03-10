package com.yk.logistic.service.item;

import org.springframework.stereotype.Service;

import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.validation.ValidationCheck;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl {
	private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ValidationCheck validationCheck;

}
