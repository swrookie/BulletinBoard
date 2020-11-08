package com.swrookie.bulletinboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.swrookie.bulletinboard.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long>
{
}
