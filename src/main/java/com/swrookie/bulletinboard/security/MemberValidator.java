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
		
		// Display error message on the form if user name input field is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
		
		// Display error message on the form if user name does not match required length
		if (memberDto.getUserName().length() < 3 || memberDto.getUserName().length() > 32)
			errors.rejectValue("userName", "Size.userForm.userName");
		
		// Display error message on the form if user name already exists
		if (memberService.findByUserName(memberDto.getUserName()) != null)
			errors.rejectValue("userName", "Duplicate.userForm.userName");
		
		// Display error message on the form if email already exists
		if (memberService.findByUserEmail(memberDto.getEmail()) != null)
			errors.rejectValue("email", "Duplicate.userForm.email");
		
		// Display error message on the form if password input field is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		
		// Display error message on the form if password does not match required length
		if (memberDto.getPassword().length() < 5 || memberDto.getPassword().length() > 32)
			errors.rejectValue("password", "Size.userForm.password");
		
		// Display error message on the form if password does not match with confirm
		if (!memberDto.getPasswordConfirm().equals(memberDto.getPassword()))
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	}
}
