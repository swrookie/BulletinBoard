package com.swrookie.bulletinboard.controller; 

 
import org.springframework.beans.factory.annotation.Autowired; 

//import java.sql.Timestamp;  
//import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.service.MemberService;

@Controller
public class LoginController 
{
	@Autowired
	private MemberService memberService;
	
	public LoginController(MemberService memberService)
	{
		this.memberService = memberService;
	}
	
	// Login Page
	@GetMapping("/")
	public String login()
	{
		return "login";
	}
	
	// Registration Page
	@GetMapping("/go_registration")
	public String registration()
	{
		return "registration";
	}
	
	@PostMapping("/registration_page")
	public String registerMember(Member member)
	{	
		memberService.createMember(member);
		
		return "login";
	}
}