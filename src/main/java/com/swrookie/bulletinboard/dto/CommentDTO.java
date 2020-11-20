package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.swrookie.bulletinboard.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class CommentDTO 
{
	private Long commentNo;			// Comment Number
	private Long boardNo;			// Post(Parent) number that contains comment(child) 
	private String author;			// Comment Author
	private String content;			// Comment content
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during create
	private Comment parentComment;
	private List<Comment> childComment = new ArrayList<Comment>();
	private Integer commentDepth;
	
	@Builder
	public CommentDTO(Long commentNo, Long boardNo, String author, String content, Timestamp createDate,
					  Comment parentComment, List<Comment> childComment, Integer commentDepth)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.parentComment = parentComment;
		this.childComment = childComment;
		this.commentDepth = commentDepth;
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
	
	public Comment toEntity()
	{
		Comment commentEntity = Comment.builder()
									   .commentNo(commentNo)
									   .boardNo(boardNo)
									   .author(this.getAuthorFromSecurity())
									   .content(content)
									   .createDate(createDate)
									   .parentComment(parentComment)
									   .childComment(childComment)
									   .commentDepth(commentDepth)
									   .build();
		System.out.println("New child comment: " + commentEntity.toString());
		return commentEntity;
	}
//	
//	// Convert DTO to Entity when creating comment reply
//	public Comment toEntity()
//	{	
//		Comment commentEntity = Comment.builder()
//				   					   .commentNo(commentNo)
//				   					   .boardNo(boardNo)
//				   					   .commentGroup(commentGroup)			
//				   					   .commentParent(commentParent)
//				   					   .commentDepth(commentDepth)
//				   					   .commentOrder(commentOrder)
//				   					   .author(this.getAuthorFromSecurity())
//				   					   .content(content)
//				   					   .createDate(createDate)
//				   					   .build();
//
//		return commentEntity;
//	}
}
