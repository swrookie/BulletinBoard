package com.swrookie.bulletinboard.controller; 

 
import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.entity.MemberRole;
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
	
	// Go to login Page when starting Spring Boot Application
	@GetMapping("/")
	public String login()
	{
		return "login";
	}
	
	@PostMapping("/do_login")
	public String doLogin()
	{
		return "redirect:/go_home";
	}
	
	@GetMapping("/do_logout")
	public String doLogout()
	{
		return "/";
	}
	
	@GetMapping("/denied")
	public String denied()
	{
		return "denied";
	}
	
	// Registration Page
	@GetMapping("/go_registration")
	public String registration()
	{
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerMember(@Validated Member userForm, BindingResult bindingResult)
	{	
		memberValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors())
		{
			log.debug("valid errors");
			return "redirect:/registration";
		}
		
		userForm.setRole(MemberRole.MEMBER);
		memberService.createMember(userForm);
		log.debug("userInfo" + userForm.toString());
		log.debug("email" + userForm.getEmail() + "|" + userForm.getPassword());
		
		return "redirect:/";
	}
}