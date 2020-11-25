package com.swrookie.bulletinboard.service;

import java.util.HashSet; 
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private MemberRepository memberRepository;
	
	@PostConstruct
	private void created()
	{
		log.debug("Check Login");
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		
		Member member = memberRepository.findByUserName(userName);
		
		if (member == null)
			throw new UsernameNotFoundException(userName + "is not found");
			
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getName()));
		
		return new User(member.getUserName(), member.getPassword(), grantedAuthorities);
	}
}
