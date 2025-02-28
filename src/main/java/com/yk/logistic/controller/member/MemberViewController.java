package com.yk.logistic.controller.member;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController{
	//동일한 login 매핑을 테스트로 작성해둔곳이 있다면 반드시 에러가난다
	@GetMapping("/login")
	public String Login() {
		return "login";
	}
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
}
