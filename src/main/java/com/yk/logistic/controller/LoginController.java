package com.yk.logistic.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yk.logistic.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	
	
	private final MemberService memberService;
	
	/* // 생성자를 통해 MemberService 주입
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }*/
	

	//로그인 기능
	@GetMapping("/login")
    public String loginPage() {
        return "login"; // login 홈페이지
    }

	//임시로그인기능 테스트 
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = memberService.login(username, password);
        return success ? "Login successful" : "Login failed";
    }

	
	
}
