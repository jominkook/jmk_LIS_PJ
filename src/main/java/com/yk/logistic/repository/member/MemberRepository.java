package com.yk.logistic.repository.member;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.Member.MemberBuilder;


public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}