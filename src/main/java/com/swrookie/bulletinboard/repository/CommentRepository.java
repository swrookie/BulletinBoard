package com.swrookie.bulletinboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> 
{
	
}
