package com.swrookie.bulletinboard.entity;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.swrookie.bulletinboard.enumeration.MemberRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="member")
@NoArgsConstructor
public class Member extends BaseTime
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long memberNo;
	private String firstName;
	private String lastName;
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	@Column(unique=true)
	private String userName;
	@Column(unique=true)
	private String email;
	private String password;
	@OneToMany(mappedBy="memberNo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Board> boards = new ArrayList<Board>();
	
	@Builder
	public Member(Long memberNo, String firstName, String lastName, MemberRole role,
				  String userName, String email, String password)
	{
		this.memberNo = memberNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
}
