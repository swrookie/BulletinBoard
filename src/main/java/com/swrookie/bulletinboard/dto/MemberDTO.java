package com.swrookie.bulletinboard.dto;

import com.swrookie.bulletinboard.entity.Member;
import com.swrookie.bulletinboard.enumeration.MemberRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class MemberDTO 
{
	private Long memberNo;
	private String firstName;
	private String lastName;
	private MemberRole role;
	private String userName;
	private String email;
	private String password;
	private String passwordConfirm;
	
	@Builder
	public MemberDTO(Long memberNo, String firstName, String lastName, String userName,
					 String email, String password)
	{
		this.memberNo = memberNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	public Member toEntity()
	{
		Member member = Member.builder()
							  .firstName(firstName)
							  .lastName(lastName)
							  .role(role)
							  .userName(userName)
							  .email(email)
							  .password(password)
							  .build();
		
		return member;
	}
}
