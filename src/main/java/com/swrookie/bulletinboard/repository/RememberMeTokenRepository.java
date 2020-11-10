package com.swrookie.bulletinboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.RememberMeToken;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Long>
{
	RememberMeToken findBySeries(String series);

	List<RememberMeToken> findByUsername(String username);
}
