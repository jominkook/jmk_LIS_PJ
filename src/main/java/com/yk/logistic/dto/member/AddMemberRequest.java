package com.yk.logistic.dto.member;

import com.yk.logistic.domain.member.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMemberRequest {
	private String name;
	private String email;
	private String password;
	private MemberRole role;
	

}
