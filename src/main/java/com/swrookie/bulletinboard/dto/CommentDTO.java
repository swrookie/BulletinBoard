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
	private Integer commentParent;
	private Integer commentDepth;
	private Integer commentOrder;
	
	@Builder
	public CommentDTO(Long commentNo, Long boardNo, String author, String content,
					  Timestamp createDate) 
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
	}
	
	public Comment toEntity()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails)
			author = ((UserDetails) principal).getUsername();
		else
			author = principal.toString();
		
		Comment commentEntity = Comment.builder()
				   					   .commentNo(commentNo)
									   .boardNo(boardNo)
									   .author(author)
									   .content(content)
									   .createDate(createDate)
									   .build();

		
		return commentEntity;
	}
}
