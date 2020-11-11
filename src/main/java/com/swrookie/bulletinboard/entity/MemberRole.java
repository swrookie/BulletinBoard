package com.swrookie.bulletinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole 
{	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	private String name;
}
