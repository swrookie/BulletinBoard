package com.swrookie.bulletinboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swrookie.bulletinboard.dto.CommentDTO;
import com.swrookie.bulletinboard.entity.Comment;
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
	
	@PostMapping("/post/{boardNo}/save")
	@ResponseBody
	public ResponseEntity<Integer> createComment(@RequestBody CommentDTO commentDto)
	{
		commentService.createComment(commentDto);
		
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
	
	// Placed parent comment info as path variable due to input stream string parse error for ajax call
	@PostMapping("/post/{boardNo}/save/{parent}")
	@ResponseBody
	public ResponseEntity<Integer> createCommentReply(@RequestBody CommentDTO commentDto, 
												 	  @PathVariable("parent") Comment parent)
	{
		commentDto.setParent(parent);
		commentService.createComment(commentDto);
		
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
	
	@PutMapping("/post/{boardNo}/comment/{commentNo}")
	@ResponseBody
	public ResponseEntity<Integer> updateComment(@RequestBody CommentDTO commentDto)
	{
		System.out.println(commentDto.toString());
		commentService.createComment(commentDto);
		
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{boardNo}/comment/{commentNo}")
	@ResponseBody
	public ResponseEntity<Integer> deleteComment(@PathVariable("commentNo") Long commentNo)
	{
		commentService.deleteComment(commentNo);		
		
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
}
