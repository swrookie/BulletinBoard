package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp;

import javax.persistence.Lob;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.swrookie.bulletinboard.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BoardDTO
{
	private Long boardNo;								// Table PK Instance Field
	private String title;								// Table Title Column Instance Field
	private String author;								// Table Author Column Instance Field
	@Lob
	private String content;								// Table Content Column Instance Field
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
	@Builder
	public BoardDTO(Long boardNo, String title, String author, String content, Timestamp createdDate, Timestamp modifiedDate)
	{
		this.boardNo = boardNo;
		this.author = author;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
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
	
	public Board toEntity()
	{
		Board boardEntity = Board.builder()
								 .boardNo(boardNo)
								 .author(this.getAuthorFromSecurity())
								 .title(title)
								 .content(content)
								 .build();
		
		return boardEntity;
	}
}
