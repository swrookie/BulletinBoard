package com.swrookie.bulletinboard.controller; 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.swrookie.bulletinboard.dto.MemberDTO;
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
	
	@GetMapping("/login?error=true")
	public String denied()
	{
		return "redirect:/";
	}
	
	// Go to Sign Up Page
	@GetMapping("/sign_up")
	public String signUp()
	{
		return "sign_up";
	}
	
	// Send sign up form to database and return to home page
	@PostMapping("/sign_up")
	public String signUp(@ModelAttribute("signUpForm") @Validated MemberDTO memberDto, BindingResult bindingResult)
	{	
		memberValidator.validate(memberDto, bindingResult);
		
		if (bindingResult.hasErrors())
		{
			log.debug("valid errors");
			return "sign_up";
		}
		
		memberDto.setRole(MemberRole.MEMBER);
		memberService.createMember(memberDto);
		log.debug("User Info: " + memberDto.toString());
		log.debug("Email: " + memberDto.getEmail() + " | " + memberDto.getPassword());
		
		return "redirect:/";
	}
}