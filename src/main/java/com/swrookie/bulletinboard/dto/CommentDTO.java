package com.swrookie.bulletinboard.dto;

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
	private Comment parent;			// Child comment's parent comment
	private Integer depth;			// Comment's depth in hierarchy
	
	@Builder
	public CommentDTO(Long commentNo, Long boardNo, String author, 
					  String content, Comment parent, Integer depth)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.parent = parent;
		this.depth = depth;
	}
	
//	public String getAuthorFromSecurity()
//	{
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if (principal instanceof UserDetails)
//			author = ((UserDetails) principal).getUsername();
//		else
//			author = principal.toString();
//		
//		return author;
//	}
	
	public Comment toEntity()
	{
		Comment commentEntity = Comment.builder()
									   .commentNo(commentNo)
									   .boardNo(boardNo)
									   .author(author)
									   .content(content)
									   .parent(parent)
									   .depth(depth)
									   .build();
		
		return commentEntity;
	}
}
