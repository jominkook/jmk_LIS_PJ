package com.yk.logistic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/test")
    public String test() {
        return "test"; // 뷰 이름 또는 리소스 경로
    }

}
