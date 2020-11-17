package com.swrookie.bulletinboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.CommentDTO;
import com.swrookie.bulletinboard.entity.Board;
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
						 .commentGroup(comment.getCommentGroup())
						 .commentParent(comment.getCommentParent())
						 .commentOrder(comment.getCommentOrder())
						 .commentDepth(comment.getCommentDepth())
						 .build();
	}
	
	private Sort sortByCommentNoDesc()
	{
		return Sort.by(Sort.Direction.DESC, "commentNo");
	}
	
	private Integer determineGroupNum()
	{
		Integer groupNum = 0;
		if (commentRepository.findAll().size() == 0)
			groupNum = 1;
		else
			groupNum = commentRepository.findAll(sortByCommentNoDesc()).get(0).getCommentGroup() + 1;
		
		return groupNum;
	}
	
	@Transactional
	public void createComment(CommentDTO commentDto)
	{
		commentRepository.save(commentDto.toEntity(determineGroupNum()));
	}
	
	@Transactional
	public List<CommentDTO> readComment(Board boardNo)
	{
		List<Comment> comments = commentRepository.findAll();
		List<CommentDTO> commentDtoList = new ArrayList<CommentDTO>();
		
		for (Comment comment : comments)
		{
			if (comment.getBoardNo().getBoardNo().equals(boardNo.getBoardNo()))
				commentDtoList.add(this.convertEntityToDto(comment));	
		}
		
		return commentDtoList;
	}
	
	@Transactional
	public CommentDTO updateComment(Long commentNo)
	{
		Comment comment = commentRepository.findById(commentNo).get();
		
		CommentDTO commentDto = CommentDTO.builder()
										  .content(comment.getContent())
										  .createDate(comment.getCreateDate())
										  .build();
		
		return commentDto;
	}
	
	
	@Transactional
	public void deleteComment(Long commentNo)
	{
		commentRepository.deleteById(commentNo);
	}
	
	public void createCommentReply() 
	{
		
	}
	
	public boolean postContainsComment(Long boardNo)
	{
		List<Comment> comments = commentRepository.findAll();
		
		if (comments.size() == 0)
			return false;
		
		return true;
	}
}
