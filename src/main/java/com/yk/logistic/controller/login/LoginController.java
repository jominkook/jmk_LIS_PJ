package com.yk.logistic.controller.login;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yk.logistic.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	
	
	private final MemberService memberService;
	
	/* // 생성자를 통해 MemberService 주입 -> @RequiredArgsConstructor 대체가능
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }*/
	

	// 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login 홈페이지
    }

    // 임시 로그인 기능 테스트
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        boolean success = memberService.login(username, password);
        if (success) {
            return "redirect:/home"; // Redirect to home page on successful login
        } else {
            redirectAttributes.addFlashAttribute("error", "Login failed");
            return "redirect:/login"; // Redirect back to login page on failure
        }
    }
	
}
