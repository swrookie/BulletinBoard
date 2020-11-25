package com.swrookie.bulletinboard.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.swrookie.bulletinboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>
{
	Member findByEmail(String email);
	Member findByUserName(String userName);
}
