package com.swrookie.bulletinboard.service;

import java.util.HashSet; 
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyUserService implements UserDetailsService
{
	private MemberRepository memberRepository;
	private static final String Role_PREFIX = "Role_";
	
	@PostConstruct
	private void created()
	{
		log.debug("체크 로그인");
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		
		Member member = memberRepository.findByEmail(email);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(Role_PREFIX + member.getRole().getName()));
		
		return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
	}
}
