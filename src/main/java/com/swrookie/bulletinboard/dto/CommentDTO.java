package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp;

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
	private Comment parent;
	private Integer depth;
	
	@Builder
	public CommentDTO(Long commentNo, Long boardNo, String author, String content, Timestamp createDate,
					  Comment parent, Integer depth)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.parent = parent;
		this.depth = depth;
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
									   .parent(parent)
									   .depth(depth)
									   .build();
		
		return commentEntity;
	}
}
