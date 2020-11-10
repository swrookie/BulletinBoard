package com.swrookie.bulletinboard.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().
			 antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/h2-console/**",
					 	 "/webjars/**", "/signup", "/home", "/").
			 permitAll().
			 anyRequest().authenticated().
			 and().
			 headers().
			 frameOptions().disable().
			 and().
			 csrf().ignoringAntMatchers("/h2-console/**").
			 and().
			 formLogin().
			 loginPage("/login").
			 loginProcessingUrl("/sign-in").
			 defaultSuccessUrl("/welcome").
			 failureUrl("/login?error").
			 usernameParameter("email").
			 passwordParameter("passwd").
			 permitAll().and().exceptionHandling().accessDeniedHandler().
			 and().
			 rememberMe().
			 key("jpub").
			 rememberMeParameter("remember-me").
			 rememberMeCookieName("jpubcookie").
			 tokenValiditySeconds(86400).
			 tokenRepository(rememberMeTokenService()).userDetailsService(myUserService()).
			 and().
			 logout().
			 invalidateHttpSession(true).
			 clearAuthentication(true).
			 logoutReqeustMatcher(new antPathRequestMatcher("/logout")).
			 logoutSuccessUrl("/login?logout").
			 permitAll();
	}
}
