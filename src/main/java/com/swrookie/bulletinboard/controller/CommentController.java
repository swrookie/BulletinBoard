package com.swrookie.bulletinboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.swrookie.bulletinboard.dto.CommentDTO;
import com.swrookie.bulletinboard.service.CommentService;

@Controller
public class CommentController 
{
	@Autowired
	private CommentService commentService;
	
	public CommentController(CommentService commentService)
	{
		this.commentService = commentService;
	}
	
	@PostMapping("/save_comment")
	public String createComment(CommentDTO commentDto)
	{
		commentService.createComment(commentDto);
		
		return "redirect:/";
	}
	
	@PostMapping("/save_comment/{commentNo}")
	public String createCommentReply(@PathVariable("commentNo") Long parentCommentNo)
	{
		return "redirect:/";
	}
	
	@PostMapping("/delete_comment")
	public String deleteComment(Long commentNo)
	{
		commentService.deleteComment(commentNo);
		
		return "redirect:/";
	}
}
