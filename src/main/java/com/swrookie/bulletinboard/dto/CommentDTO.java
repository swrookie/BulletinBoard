package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp; 

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.swrookie.bulletinboard.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommentDTO 
{
	private Long commentNo;			// Comment Number
	private Long boardNo;			// Post(Parent) number that contains comment(child) 
	private String author;			// Comment Author
	private String content;			// Comment content
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during create
	private Integer commentGroup;
	private Long commentParent;
	private Integer commentDepth;
	private Integer commentOrder;
	
	@Builder
	public CommentDTO(Long commentNo, Long boardNo, String author, String content, Timestamp createDate,
					  Integer commentGroup, Long commentParent, Integer commentDepth, Integer commentOrder) 
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.commentGroup = commentGroup;
		this.commentParent = commentParent;
		this.commentDepth = commentDepth;
		this.commentOrder = commentOrder;
	}
	
	public String getAuthorFromSecurity()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails)
			author = ((UserDetails) principal).getUsername();
		else
			author = principal.toString();
		
		return author;
	}
	
	// Convert DTO to Entity when creating the outermost parent comment
	public Comment toEntity(Integer groupNum, Integer orderNum)
	{
		commentGroup = groupNum;
		commentParent = 0L;
		commentDepth = 0;
		commentOrder = orderNum;
		
		Comment commentEntity = Comment.builder()
				   					   .commentNo(commentNo)
									   .boardNo(boardNo)
									   .commentGroup(commentGroup)			
									   .commentParent(commentParent)
									   .commentDepth(commentDepth)
									   .commentOrder(commentOrder)
									   .author(this.getAuthorFromSecurity())
									   .content(content)
									   .createDate(createDate)
									   .build();

		return commentEntity;
	}
	
	// Convert DTO to Entity when creating comment reply
	public Comment toEntity()
	{	
		Comment commentEntity = Comment.builder()
				   					   .commentNo(commentNo)
				   					   .boardNo(boardNo)
				   					   .commentGroup(commentGroup)			
				   					   .commentParent(commentParent)
				   					   .commentDepth(commentDepth)
				   					   .commentOrder(commentOrder)
				   					   .author(this.getAuthorFromSecurity())
				   					   .content(content)
				   					   .createDate(createDate)
				   					   .build();

		return commentEntity;
	}
	
	// Convert DTO to Entity for updating orders of the child comments when creating comment reply
	public Comment toEntity(Integer order)
	{
		Integer nOrder = commentOrder + order;
		
		Comment commentEntity = Comment.builder()
				   					   .commentNo(commentNo)
				   					   .boardNo(boardNo)
				   					   .commentGroup(commentGroup)			
				   					   .commentParent(commentParent)
				   					   .commentDepth(commentDepth)
				   					   .commentOrder(nOrder)
				   					   .author(this.getAuthorFromSecurity())
				   					   .content(content)
				   					   .createDate(createDate)
				   					   .build();

		return commentEntity;
	}
}
