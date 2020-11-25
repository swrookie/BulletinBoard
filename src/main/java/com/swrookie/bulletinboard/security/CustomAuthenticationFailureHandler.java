package com.swrookie.bulletinboard.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
												implements AuthenticationFailureHandler
{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
										AuthenticationException exception) throws IOException, ServletException
	{
		if (exception instanceof AuthenticationServiceException) 
			exception = new AuthenticationServiceException("Not Authenticated");
		else if (exception instanceof UsernameNotFoundException)
			exception = new UsernameNotFoundException("Username not found");
		else if (exception instanceof BadCredentialsException)
			exception = new BadCredentialsException("Bad Credentials -_-");
		else if (exception instanceof InternalAuthenticationServiceException)
			exception = new InternalAuthenticationServiceException("Internal Service Error -_-");
		
		super.setDefaultFailureUrl("/");
		super.onAuthenticationFailure(request, response, exception);
	}
}
