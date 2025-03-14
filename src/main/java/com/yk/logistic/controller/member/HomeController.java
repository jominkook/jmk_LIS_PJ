package com.yk.logistic.controller.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yk.logistic.domain.member.Member;

@Controller
public class HomeController {
	@GetMapping("/home")
    public String homePage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member) auth.getPrincipal();
        String name = member.getName();
        //Long memberId = member.getId();
        model.addAttribute("username", name);
        //model.addAttribute("memberId", memberId);
        return "home";
    }

}
