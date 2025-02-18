package com.yk.logistic.repository;






import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.logistic.domain.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);
    Optional<Member> findByEmail(String email);
}