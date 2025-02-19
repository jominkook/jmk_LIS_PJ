package com.yk.logistic.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeMemberRequest {
	String email;
	String password;
	
	@Builder
	public ChangeMemberRequest(String email,String password) {
		this.email = email;
		this.password = password;
	}

}
