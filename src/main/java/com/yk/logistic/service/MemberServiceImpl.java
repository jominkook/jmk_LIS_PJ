package com.yk.logistic.service;


import org.springframework.stereotype.Service;
import com.yk.logistic.domain.Member;
import com.yk.logistic.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberRepository memberRepository;


	public boolean login(String name, String password) {
	    try {
	        Member member = memberRepository.findByName(name);
	        return member != null && member.getPassword().equals(password);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}