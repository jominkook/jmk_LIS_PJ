package com.yk.logistic.service.member;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.member.ChangeMemberRequest;
import com.yk.logistic.dto.member.JoinMemberRequest;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.validation.ValidationCheck;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Long join(JoinMemberRequest requestDto) {
		return memberRepository.save(Member.builder()
				.email(requestDto.getEmail())
				.password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
				.build().getId();
		
	}



}