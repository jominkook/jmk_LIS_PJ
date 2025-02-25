package com.yk.logistic.dto.member;

import com.yk.logistic.domain.member.Member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberRequest {
	private String email;
	private String password;
	
}
