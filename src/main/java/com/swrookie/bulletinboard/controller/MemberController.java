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
	
	public MemberController(MemberService memberService, MemberValidator memberValidator)
	{
		this.memberService = memberService;
		this.memberValidator = memberValidator;
	}	
	
	// Authenticate in the security configuration and return to the home screen
	@PostMapping("/login")
	public String login()
	{
		return "redirect:/";
	}
	
	// Being Logged in, Go to the home page after clicking logout button
	// @PostMapping needed when implementing CSRF
	@GetMapping("/logout")
	public String logout()
	{
		return "redirect:/";
	}
	
	@GetMapping("/denied")
	public String denied()
	{
		return "denied";
	}
	
	// Go to Sign Up Page
	@GetMapping("/sign_up")
	public String signUp()
	{
		return "sign_up";
	}
	
	// Send sign up form to database and return to home page
	@PostMapping("/sign_up")
	public String signUp(@Validated Member member, BindingResult bindingResult)
	{	
		memberValidator.validate(member, bindingResult);
		
		if (bindingResult.hasErrors())
		{
			System.out.println("Error occured");
			log.debug("valid errors");
			return "redirect:/sign_up";
		}
		System.out.println("Error not occured");
		member.setRole(MemberRole.MEMBER);
		memberService.createMember(member);
		log.debug("User Info: " + member.toString());
		log.debug("Email: " + member.getEmail() + " | " + member.getPassword());
		
		return "redirect:/";
	}
}