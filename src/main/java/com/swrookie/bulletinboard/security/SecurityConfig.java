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
		// CSRF Disabled
		http.csrf().disable().authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("go_home/**").hasRole("MEMBER")
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
				.and()
			.formLogin()
			.usernameParameter("userName")
			.passwordParameter("password")
			.loginPage("/")
			.loginProcessingUrl("/do_login")
			.defaultSuccessUrl("/")
			.permitAll()
				.and()
			.exceptionHandling().accessDeniedHandler(MemberAccessDeniedHandler())
				.and()
			.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/do_logout"))
        	.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.clearAuthentication(true);
		
		// CSRF Enabled
//		http.authorizeRequests()
//			.antMatchers("/admin/**/").hasRole("ADMIN")
//			.antMatchers("/do_login/**").hasRole("MEMBER")
//			.antMatchers("/**").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.headers()
//			.frameOptions().disable()
//			.and()
//			.csrf().ignoringAntMatchers("/h2-console/**").
//			.and()
//			.formLogin().
//			.loginPage("/")
//			.loginProcessingUrl("/do_login")
//			.defaultSuccessUrl("/go_home")
//			.failureUrl("/login?error")
//			.usernameParameter("userName")
//			.passwordParameter("password")
//			.permitAll().and().exceptionHandling().accessDeniedHandler(MemberAccessDeniedHandler())
//			.and()
//			.logout()
//			.invalidateHttpSession(true)
//		    .clearAuthentication(true)
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//			.logoutSuccessUrl("/login?logout")
//			.permitAll()
//			.and()
//			.rememberMe()
//			.key("swrookie")
//			.rememberMeParameter("remember-me")
//			.rememberMeCookieName("swrookiecookie")
//			.tokenValiditySeconds(86400)
//			.tokenRepository(rememberMeTokenService()).userDetailsService(myUserService());
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
