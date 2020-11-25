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
	
	@PutMapping("/post/{boardNo}/comment/{commentNo}")
	@ResponseBody
	public ResponseEntity<Integer> updateComment(@RequestBody CommentDTO commentDto)
	{
		System.out.println(commentDto.toString());
//		commentService.createComment(commentDTO);
		
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
