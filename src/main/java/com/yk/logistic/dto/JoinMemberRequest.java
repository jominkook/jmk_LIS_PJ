package com.yk.logistic.dto;

import com.yk.logistic.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
