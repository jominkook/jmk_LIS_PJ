package com.yk.logistic.controller.login;



import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yk.logistic.constant.SessionConst;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.service.member.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    //로그인세션 기능처리
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Member member = memberService.login(username, password);
        if (member != null) {
            session.setAttribute(SessionConst.LOGIN_ID, member.getId());
            return "redirect:/items/register";
        } else {
            return "redirect:/login?error";
        }
    }
}
	

