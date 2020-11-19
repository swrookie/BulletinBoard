package com.swrookie.bulletinboard.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.enumeration.MemberRole;
import com.swrookie.bulletinboard.security.MemberValidator;
import com.swrookie.bulletinboard.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController 
{
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberValidator memberValidator;
	
	public MemberController(MemberService memberService)
	{
		this.memberService = memberService;
	}	
	
	// Authenticate in the security configuration and return to the home screen
	@PostMapping("/do_login")
	public String doLogin()
	{
		return "redirect:/";
	}
	
	// Being Logged in, Go to the home page after clicking logout button
	// @PostMapping needed when implementing CSRF
	@GetMapping("/do_logout")
	public String doLogout()
	{
		return "redirect:/";
	}
	
	@GetMapping("/denied")
	public String denied()
	{
		return "denied";
	}
	
	// Registration Page
	@GetMapping("/register")
	public String registration()
	{
		return "registration";
	}
	
	@PostMapping("/do_register")
	public String registerMember(@Validated Member member, BindingResult bindingResult)
	{	
		memberValidator.validate(member, bindingResult);
		
		if (bindingResult.hasErrors())
		{
			log.debug("valid errors");
			return "redirect:/registration";
		}
		
		member.setRole(MemberRole.MEMBER);
		memberService.createMember(member);
		log.debug("User Info: " + member.toString());
		log.debug("Email: " + member.getEmail() + " | " + member.getPassword());
		
		return "redirect:/go_login";
	}
}