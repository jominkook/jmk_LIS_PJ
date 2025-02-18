package com.yk.logistic.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yk.logistic.dto.JoinMemberRequest;
import com.yk.logistic.service.MemberService;


import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	

	private final MemberService memberService;
	
	@GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // 회원가입 폼 페이지
    }

    @PostMapping("/register")
    public String registerMember(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam("re-password") String rePassword) {
        if (!password.equals(rePassword)) {
            // 비밀번호와 재입력 비밀번호가 일치하지 않으면 에러 처리
            return "redirect:/member/register?error=passwordMismatch";
        }

        JoinMemberRequest request = new JoinMemberRequest(name, email, password);
        memberService.join(request);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
	
	
}
