package com.yk.logistic.service.member;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final MemberRepository memberRepository;

	@Override
	public Member loadUserByUsername(String email) {
		return memberRepository.findByEmail(email)
				.orElseThrow(()-> new IllegalArgumentException(email));
	}

}
