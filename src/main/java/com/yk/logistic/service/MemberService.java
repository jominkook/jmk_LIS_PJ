package com.yk.logistic.service;

import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;

public interface MemberService {
	public boolean login(String name, String password);
	public Long join(JoinMemberRequest requestDto);
    public Long changePassword(ChangeMemberRequest requestDto);
}