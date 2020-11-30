package com.swrookie.bulletinboard.service;

import java.util.ArrayList; 
import java.util.List;

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
				         .parent(comment.getParent())
				         .depth(comment.getDepth())
				         .build();
	}
	
	// Add dto to the list for displaying comments on the view with DFS
	private void addEntityToDtoDfs(List<Comment> comments, 
								   List<CommentDTO> commentDtoList, 
								   List<Long> commentNoList)
	{
		for (Comment comment : comments)
		{
			if (!commentNoList.contains(comment.getCommentNo()))
			{
				commentDtoList.add(this.convertEntityToDto(comment));
				commentNoList.add(comment.getCommentNo());
				if (!comment.getChildren().isEmpty())
					addEntityToDtoDfs(comment.getChildren(), commentDtoList, commentNoList);
			}
		}
	}
	
	@Transactional
	public void createComment(CommentDTO commentDto)
	{
		if (commentDto.getCommentNo() == null)
		{
			if (commentDto.getParent() == null)
				commentDto.setDepth(0);
			else
				commentDto.setDepth(commentDto.getParent().getDepth() + 1);
		}
		
		commentRepository.save(commentDto.toEntity());
	}
	
	@Transactional
	public List<CommentDTO> readComment(Long boardNo)
	{
		List<Comment> comments = commentRepository.findByBoardNoOrderByCommentNoAsc(boardNo);
		List<CommentDTO> commentDtoList = new ArrayList<CommentDTO>();
		List<Long> noList = new ArrayList<Long>();
		
		this.addEntityToDtoDfs(comments, commentDtoList, noList);
		
		return commentDtoList;
	}
	
	@Transactional
	public void deleteComment(Long commentNo)
	{
		commentRepository.deleteById(commentNo);
	}
}
