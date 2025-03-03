package com.yk.logistic.repository.member;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yk.logistic.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}