package com.yk.logistic.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.yk.logistic.service.MemberService;

@Controller
public class LoginController {
	
	
	private final MemberService memberService = null;
	
	//로그인 기능
	@GetMapping("/login")
    public String loginPage() {
        return "login"; // login 홈페이지
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = memberService.login(username, password);
        return success ? "Login successful" : "Login failed";
    }

	
	
}
