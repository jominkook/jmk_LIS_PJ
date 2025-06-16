package com.yk.logistic.service.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.member.AddMemberRequest;
import com.yk.logistic.repository.member.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long save(AddMemberRequest dto) {
        return memberRepository.save(Member.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 비밀번호 암호화
                .name(dto.getName()) // name 필드 추가
                .build()).getId();
    }
    
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return member;
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}