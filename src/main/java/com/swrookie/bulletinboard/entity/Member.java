package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@Getter
@Entity
@Table(name="member")
public class Member 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long memberNo;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private String email;
	private String password;
	@CreationTimestamp
	private Timestamp createDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	
	public Member()
	{
	}
	
	public Member(Long memberNo, String firstName, String lastName, String gender, String birthDate,
			      String email, String password, Timestamp createDate)
	{
		this.memberNo = memberNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.createDate = createDate;
	}
}
