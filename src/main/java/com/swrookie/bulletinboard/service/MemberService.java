package com.swrookie.bulletinboard.service;

import javax.transaction.Transactional; 

import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.repository.MemberRepository;

@Service
public class MemberService 
{
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}
	
	@Transactional
	public void createMember(Member newMember)
	{
//		String firstName = newMember.getFirstName();	
//		String lastName = newMember.getLastName();		
//		String birthDate = newMember.getBirthDate();	
//		String gender = newMember.getGender();
//		String email = newMember.getEmail();
//		String password = newMember.getPassword();
//		
//		memberRepository.save(new Member(firstName, lastName, birthDate, gender,
//				  			  email, password, Timestamp.valueOf(LocalDateTime.now())));
		memberRepository.save(newMember);
	}
}
