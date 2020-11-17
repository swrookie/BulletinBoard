package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.swrookie.bulletinboard.entity.Board;
import com.swrookie.bulletinboard.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommentDTO 
{
	private Long commentNo;			// Comment Number
	private Board boardNo;			// Post(Parent) number that contains comment(child) 
	private String author;			// Comment Author
	private String content;			// Comment content
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during create
	private Integer commentGroup;
	private Long commentParent;
	private Integer commentDepth;
	private Integer commentOrder;
	
	@Builder
	public CommentDTO(Long commentNo, Board boardNo, String author, String content, Timestamp createDate,
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
	
	public Comment toEntity(Integer groupNum)
	{
		commentGroup = groupNum;
		Comment commentEntity = Comment.builder()
				   					   .commentNo(commentNo)
									   .boardNo(boardNo)
									   .commentGroup(commentGroup)			// Outermost parent is same as it's id
									   .commentParent(commentParent)
									   .commentDepth(commentDepth)
									   .commentOrder(commentOrder)
									   .author(this.getAuthorFromSecurity())
									   .content(content)
									   .createDate(createDate)
									   .build();

		return commentEntity;
	}
	
	public Comment replyToEntity(Integer depth, Integer order)
	{
		Comment comment = new Comment();
		
		return comment;
	}
}
