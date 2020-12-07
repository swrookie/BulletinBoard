package com.swrookie.bulletinboard.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.entity.RememberMeToken;
import com.swrookie.bulletinboard.repository.RememberMeTokenRepository;


@Service
@Transactional
public class RememberMeTokenService implements PersistentTokenRepository
{
	@Autowired
	private RememberMeTokenRepository rememberMeTokenRepository;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token)
	{
		RememberMeToken newToken = new RememberMeToken(token);
		this.rememberMeTokenRepository.save(newToken);
	}
	
	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed)
	{
		RememberMeToken token = this.rememberMeTokenRepository.findBySeries(series);
		
		if (token != null)
		{
			token.setToken(tokenValue);
			token.setLastUsed(lastUsed);
			this.rememberMeTokenRepository.save(token);
		}
	}
	
	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId)
	{
		RememberMeToken token = this.rememberMeTokenRepository.findBySeries(seriesId);
		
		if (token != null)
			return new PersistentRememberMeToken(token.getUserName(), token.getSeries(), 
												 token.getToken(), token.getLastUsed());
		else
			return null;
	}
	
	@Override
	public void removeUserTokens(String userName)
	{
		List<RememberMeToken> tokens = this.rememberMeTokenRepository.findByUserName(userName);
		this.rememberMeTokenRepository.deleteAll(tokens);
	}
}
