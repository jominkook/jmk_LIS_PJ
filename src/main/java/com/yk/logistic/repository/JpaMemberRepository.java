package com.yk.logistic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yk.logistic.domain.Member;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {
	public Optional<Member> findByEmail(String email);

}
