package com.yk.logistic.dto.member;

import com.yk.logistic.domain.member.Member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor //모든 필드를 받는 생성자 생성
@NoArgsConstructor //기본 생성자 생성
public class JoinMemberRequest {
	private String name;
	private String email;
	private String password;
	
	@Builder
	public JoinMemberRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	
	public Member toEntity() {
		return Member.builder()
				.name(name)
				.email(email)
				.password(password)
				.build();
	}
	
}
