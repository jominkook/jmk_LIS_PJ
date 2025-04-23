package com.yk.logistic.controller.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yk.logistic.domain.member.Member;

@Controller
@RequestMapping("/view")
public class HomeViewController {
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal(); 

        String username = member.getName();
        Long userId = member.getId();

        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
        return "index"; 
    }
}
