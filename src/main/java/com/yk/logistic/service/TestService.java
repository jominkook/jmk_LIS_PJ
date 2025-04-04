package com.yk.logistic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;



@Service
public class TestService {
	
	@Autowired
	MemberRepository memberRepository;
	
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}
	
}
