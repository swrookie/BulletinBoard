package com.swrookie.bulletinboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.service.MemberService;

@Component
public class MemberValidator implements Validator 
{
	@Autowired
	private MemberService memberService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Member.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		Member member = (Member) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
		if (member.getUserName().length() < 3 || member.getUserName().length() > 32)
			errors.rejectValue("userName", "Size.userForm.userName");
		
		if (memberService.findByUserName(member.getUserName()) != null)
			errors.rejectValue("userName", "Duplicate.userForm.userName");
		
		if (memberService.findByUserEmail(member.getEmail()) != null)
			errors.rejectValue("email", "Duplicate.userForm.email");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (member.getPassword().length() < 5 || member.getPassword().length() > 32)
			errors.rejectValue("password", "Size.userForm.password");
		
		if (!member.getPasswordConfirm().equals(member.getPassword()))
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	}
}
