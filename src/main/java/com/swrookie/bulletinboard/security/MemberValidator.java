package com.swrookie.bulletinboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.swrookie.bulletinboard.dto.MemberDTO;
import com.swrookie.bulletinboard.service.MemberService;

@Component
public class MemberValidator implements Validator 
{
	@Autowired
	private MemberService memberService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return MemberDTO.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		MemberDTO memberDto = (MemberDTO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
		if (memberDto.getUserName().length() < 3 || memberDto.getUserName().length() > 32)
			errors.rejectValue("userName", "Size.userForm.userName");
		
		if (memberService.findByUserName(memberDto.getUserName()) != null)
			errors.rejectValue("userName", "Duplicate.userForm.userName");
		
		if (memberService.findByUserEmail(memberDto.getEmail()) != null)
			errors.rejectValue("email", "Duplicate.userForm.email");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (memberDto.getPassword().length() < 5 || memberDto.getPassword().length() > 32)
			errors.rejectValue("password", "Size.userForm.password");
		
		if (!memberDto.getPasswordConfirm().equals(memberDto.getPassword()))
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	}
}
