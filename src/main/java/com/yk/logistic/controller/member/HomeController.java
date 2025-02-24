package com.yk.logistic.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home")
    public String homePage() {
        return "home"; // home.html 페이지 반환
    }

}
