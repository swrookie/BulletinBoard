package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="member")
@ToString
public class Member
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long memberNo;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	private String username;
	private String password;
	private String passwordConfirm;
	@Column(unique = true)
	private String email;
	@CreationTimestamp
	private Timestamp createDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	
	public Member()
	{
	}
	
//	public Member(Long memberNo, String firstName, String lastName, String gender, String birthDate,
//			      String username, String email, String password, String passwordConfirm, MemberRole role,
//			      Timestamp createDate)
//	{
//		this.memberNo = memberNo;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.gender = gender;
//		this.birthDate = birthDate;
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.passwordConfirm = passwordConfirm;
//		this.role = role;
//		this.createDate = createDate;
//	}
}
