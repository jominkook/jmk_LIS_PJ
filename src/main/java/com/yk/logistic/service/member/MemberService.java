package com.yk.logistic.service.member;

import com.yk.logistic.dto.member.ChangeMemberRequest;
import com.yk.logistic.dto.member.JoinMemberRequest;

public interface MemberService {
	public Long join(JoinMemberRequest requestDto);
    public Long changePassword(ChangeMemberRequest requestDto);
}