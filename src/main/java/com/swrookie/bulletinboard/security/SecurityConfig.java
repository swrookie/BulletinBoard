package com.swrookie.bulletinboard.security;

import java.util.EnumSet; 

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.swrookie.bulletinboard.service.MyUserService;
import com.swrookie.bulletinboard.service.RememberMeTokenService;
import com.swrookie.bulletinboard.service.MemberAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests()
			.antMatchers("/do_login/go_home/**/").hasRole("MEMBER")
			.antMatchers("/admin/**/").hasRole("ADMIN")
			.anyRequest().permitAll()
				.and()
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/do_login")
			.usernameParameter("userName")
			.passwordParameter("password")
			.defaultSuccessUrl("/go_home")
			.permitAll()
				.and()
			.exceptionHandling().accessDeniedHandler(MemberAccessDeniedHandler())
				.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
        	.logoutRequestMatcher(new AntPathRequestMatcher("/do_logout"))
        	.logoutSuccessUrl("/")
        	.permitAll();
		
		
//		http.authorizeRequests().
//			 anyRequest().authenticated().
//			 and().
//			 headers().
//			 frameOptions().disable().
//			 and().
//			 csrf().ignoringAntMatchers("/h2-console/**").
//			 and().
//			 formLogin().
//			 loginPage("/login").
//			 loginProcessingUrl("/registration").
//			 defaultSuccessUrl("/home_post_read").
//			 failureUrl("/login?error").
//			 usernameParameter("email").
//			 passwordParameter("passwd").
//			 permitAll().and().exceptionHandling().accessDeniedHandler(MemberAccessDeniedHandler()).
//			 and().
//			 logout().
//			 invalidateHttpSession(true).
//			 clearAuthentication(true).
//			 logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
//			 logoutSuccessUrl("/login?logout").
//			 permitAll().
//			 and().
//			 rememberMe().
//			 key("jpub").
//			 rememberMeParameter("remember-me").
//			 rememberMeCookieName("jpubcookie").
//			 tokenValiditySeconds(86400).
//			 tokenRepository(rememberMeTokenService()).userDetailsService(myUserService());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", 
								   "/h2-console/**", "/webjars/**", "/signup", "/home", "/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(myUserService()).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	public FilterRegistrationBean<Filter> getSpringSecurityFilterChainBindedToError(
			@Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain)
	{
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(springSecurityFilterChain);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		
		return registration;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MyUserService myUserService() throws Exception
	{
		return new MyUserService();
	}
	
	@Bean
	public RememberMeTokenService rememberMeTokenService() throws Exception
	{
		return new RememberMeTokenService();
	}
	
	@Bean
	public MemberAccessDeniedHandler MemberAccessDeniedHandler() throws Exception
	{
		return new MemberAccessDeniedHandler();
	}
}
