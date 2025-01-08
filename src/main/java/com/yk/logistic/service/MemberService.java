package com.yk.logistic.service;

import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;

public interface MemberService {
	public Long join(JoinMemberRequest request);
	public Long changePassword(ChangeMemberRequest request);
	
}