package com.swrookie.bulletinboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.CommentDTO;
import com.swrookie.bulletinboard.entity.Comment;
import com.swrookie.bulletinboard.repository.CommentRepository;

@Service
public class CommentService 
{
	@Autowired
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository)
	{
		this.commentRepository = commentRepository;
	}
	
	private CommentDTO convertEntityToDto(Comment comment)
	{
		return CommentDTO.builder()
						 .commentNo(comment.getCommentNo())
						 .boardNo(comment.getBoardNo())
						 .author(comment.getAuthor())
						 .content(comment.getContent())
						 .createDate(comment.getCreateDate())
						 .build();
	}
	
	@Transactional
	public void createComment(CommentDTO commentDto)
	{
		commentRepository.save(commentDto.toEntity()).getCommentNo();
	}
	
	@Transactional
	public List<CommentDTO> readComment(Long boardNo)
	{
		List<Comment> comments = commentRepository.findAll();
		List<CommentDTO> commentDtoList = new ArrayList<CommentDTO>();
		
		for (Comment comment : comments)
		{
			if (comment.getBoardNo().equals(boardNo))
				commentDtoList.add(this.convertEntityToDto(comment));
		}
		
		return commentDtoList;
	}
	
	@Transactional
	public CommentDTO updateComment(Long commentNo)
	{
		Optional<Comment> commentWrapper = commentRepository.findById(commentNo);
		Comment comment = commentWrapper.get();
		
		CommentDTO commentDto = CommentDTO.builder()
										  .content(comment.getContent())
										  .createDate(comment.getCreateDate())
										  .build();
		
		return commentDto;
	}
	
	public void replyToComment(Comment comment)
	{
		if (comment.getCommentNo().equals(null))
		{
			if (!comment.getCommentParent().equals(null))
			{
				Comment commentInfo;
			}
		}
	}
	
	@Transactional
	public void deleteComment(Long commentNo)
	{
		commentRepository.deleteById(commentNo);
	}
}
