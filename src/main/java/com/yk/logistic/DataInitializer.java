package com.yk.logistic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 미리 데이터를 삽입합니다.
        Member member1 = Member.builder()
                .email("user1@example.com")
                .password(bCryptPasswordEncoder.encode("password1"))
                .name("jominkook")
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .email("user2@example.com")
                .password(bCryptPasswordEncoder.encode("password2"))
                .name("jominsoo")
                .build();
        memberRepository.save(member2);

        // 필요한 만큼 데이터를 추가합니다.
    }
}