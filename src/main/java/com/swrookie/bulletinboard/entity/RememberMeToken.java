package com.swrookie.bulletinboard.entity;

import java.io.Serializable; 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="persistent_logins")
@NoArgsConstructor
public class RememberMeToken implements Serializable
{
	@Id
	@Column(name = "series")
	private String series;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "token", nullable = false)
	private String token;
	
	@Column(name = "last_used", nullable = false)
	private Date lastUsed;
	
	public RememberMeToken(PersistentRememberMeToken token)
	{
		this.series = token.getSeries();
		this.username = token.getUsername();
		this.token = token.getTokenValue();
		this.lastUsed = token.getDate();
	}
}
