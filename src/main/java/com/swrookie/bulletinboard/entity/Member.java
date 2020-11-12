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
	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	private String userName;
	@Column(unique = true)
	private String email;
	private String password;
	private String passwordConfirm;
	@CreationTimestamp
	private Timestamp createDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	
	public Member()
	{
	}
}
