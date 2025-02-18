package com.yk.logistic.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yk.logistic.domain.Member;
import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;
import com.yk.logistic.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	

	private final MemberRepository memberRepository;
	
	
	private final ValidationCheck validationCheck;


	public boolean login(String name, String password) {
	    try {
	        Member member = memberRepository.findByName(name);
	        return member != null && member.getPassword().equals(password);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	@Transactional
    public Long join(JoinMemberRequest requestDto) {
        validationCheck.validateDuplicate(memberRepository.findByEmail(requestDto.getEmail()));
        return memberRepository.save(requestDto.toEntity()).getId();
    }
	
    @Override
    @Transactional
    public Long changePassword(ChangeMemberRequest requestDto) {
        Member findMember = validationCheck.getMember(memberRepository.findByEmail(requestDto.getEmail()));
        findMember.changePassword(requestDto.getPassword());
        return findMember.getId();
    }
}