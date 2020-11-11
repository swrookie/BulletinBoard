package com.swrookie.bulletinboard.controller; 

 
import org.springframework.beans.factory.annotation.Autowired; 

//import java.sql.Timestamp;  
//import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
	
	// Dummy method for home page before login for security
	@GetMapping("/")
	public String home()
	{
		return "";
	}
	
	// Login Page
	@GetMapping("/login")
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
	public String registerMember(@ModelAttribute("userForm") @Validated Member userForm, BindingResult bindingResult)
	{	
		memberValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors())
		{
			log.debug("valid errors");
			return "registration";
		}
		
		userForm.setRole(MemberRole.USER);
		memberService.createMember(userForm);
		log.debug("userInfo" + userForm.toString());
		log.debug("email" + userForm.getEmail() + "|" + userForm.getPassword());
		
		return "redirect:/login";
	}
}