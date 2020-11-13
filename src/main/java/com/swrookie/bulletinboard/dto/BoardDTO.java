package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp;   

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.swrookie.bulletinboard.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BoardDTO
{
	private Long boardNo;								// Table PK Instance Field
	private String title;								// Table Title Column Instance Field
	private String author;								// Table Author Column Instance Field
	private String content;								// Table Content Column Instance Field
	@CreationTimestamp
	private Timestamp createDate;		// LocalDateTime
	@UpdateTimestamp
	private Timestamp updateDate;	// LocalDateTime
	
	@Builder
	public BoardDTO(Long boardNo, String title, String author, 
					String content, Timestamp createDate, Timestamp updateDate)
	{
		this.boardNo = boardNo;
		this.title = title;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public Board toEntity()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails)
			author = ((UserDetails) principal).getUsername();
		else
			author = principal.toString();
		
		Board boardEntity = Board.builder()
								 .boardNo(boardNo)
								 .title(title)
								 .author(author)
								 .content(content)
								 .createDate(createDate)
								 .build();
		
		return boardEntity;
	}
}
