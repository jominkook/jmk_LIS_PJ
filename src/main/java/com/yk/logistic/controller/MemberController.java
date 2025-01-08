package com.yk.logistic.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;
import com.yk.logistic.service.MemberService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("/join")
	public Long join(@RequestBody JoinMemberRequest request) {
		return memberService.join(request);
	}
	
	@PutMapping("/changePw")
	public Long changePassword(@RequestBody ChangeMemberRequest request) {
		return memberService.changePassword(request);
	}
	


}
