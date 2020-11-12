package com.swrookie.bulletinboard.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.repository.MemberRepository;

@Service
public class MemberService 
{
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public MemberService(MemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}
	
	public Member findByUserEmail(String email)
	{
		return memberRepository.findByEmail(email);
	}
	
	public Member findByUserName(String userName)
	{
		return memberRepository.findByUserName(userName);
	}
	
	@Transactional
	public void createMember(Member member)
	{
		member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
		member.setPasswordConfirm(bCryptPasswordEncoder.encode(member.getPasswordConfirm()));
		memberRepository.save(member);
	}
}
