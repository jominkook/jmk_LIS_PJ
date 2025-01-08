package com.yk.logistic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yk.logistic.domain.Member;
import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;
import com.yk.logistic.repository.JpaMemberRepository;


import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final JpaMemberRepository memberRepository;
	private final VaildationCheck validationCheck;
	
	@Override
	@Transactional
	public Long join(JoinMemberRequest request) {
		validationCheck.validateDuplicate(memberRepository.findByEmail(request.getEmail()));
		return memberRepository.save(request.toEntity()).getId();
	}
	
	@Override
	@Transactional
	public Long changePassword(ChangeMemberRequest request) {
		Member findMember = validationCheck.getMember(memberRepository.findByEmail(request.getEmail()));
		findMember.changePassword(request.getPassword());
		return findMember.getId();
	}
	

}
