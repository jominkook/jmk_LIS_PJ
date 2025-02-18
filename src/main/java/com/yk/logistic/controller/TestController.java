package com.yk.logistic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.logistic.domain.Member;
import com.yk.logistic.service.TestService;

@RestController
public class TestController {
	 /*@Autowired
	 TestService testService;
	 
	 @GetMapping("/test")
	 public List<Member> getAllMembers() {
		 List<Member> members = testService.getAllMembers();
		 return members;
	 }*/

}