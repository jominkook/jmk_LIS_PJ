package com.yk.logistic.controller.member;

import com.yk.logistic.constant.SessionConst;
import com.yk.logistic.domain.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.yk.logistic.dto.member.AddMemberRequest;
import com.yk.logistic.service.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(AddMemberRequest request) {
        memberService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    //동일한 login 매핑을 테스트로 작성해둔곳이 있다면 반드시 에러가난다
    @PostMapping("/login")
    public String login(@RequestParam String useremail, @RequestParam String password, HttpSession session) {
        Member member = memberService.login(useremail, password);
        if (member != null) {
            session.setAttribute(SessionConst.LOGIN_ID, member.getId());
            return "/items";
        } else {
        	System.out.println("Login failed for useremail: " + useremail);
            return "redirect:/login?error";
        }
    }
}