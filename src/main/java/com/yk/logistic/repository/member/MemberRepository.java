package com.yk.logistic.repository.member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.member.AddMemberRequest;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Member save(AddMemberRequest requestDto);
}