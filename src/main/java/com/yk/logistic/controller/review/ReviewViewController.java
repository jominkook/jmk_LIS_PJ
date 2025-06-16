package com.yk.logistic.controller.review;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/reviews")
public class ReviewViewController {

    @GetMapping("/register")
    public String ReviewRegisterForm(@RequestParam Long itemId, @RequestParam(required = false) Long revieweeId, Model model) {
        model.addAttribute("itemId", itemId); // 아이템 ID를 모델에 추가
        model.addAttribute("revieweeId", revieweeId); // 리뷰 대상자 ID를 모델에 추가
        return "register-review"; // templates/register-review.html을 반환
    }
}