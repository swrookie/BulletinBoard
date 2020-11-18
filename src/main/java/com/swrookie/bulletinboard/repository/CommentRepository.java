package com.swrookie.bulletinboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swrookie.bulletinboard.entity.Board;
import com.swrookie.bulletinboard.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> 
{
	List<Comment> findByCommentOrderGreaterThanEqual(Integer OrderNum);
	List<Comment> findByBoardNoOrderByCommentOrderAsc(Board boardNo);
}
