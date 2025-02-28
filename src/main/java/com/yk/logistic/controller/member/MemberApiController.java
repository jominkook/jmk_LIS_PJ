package com.yk.logistic.controller.member;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yk.logistic.dto.member.AddMemberRequest;
import com.yk.logistic.service.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberApiController {
	private final MemberService memberService;
	
	@PostMapping("/member")
	public String signup(AddMemberRequest request) {
		memberService.save(request);
		return "redirect:/login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request,response,
	SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}

}
