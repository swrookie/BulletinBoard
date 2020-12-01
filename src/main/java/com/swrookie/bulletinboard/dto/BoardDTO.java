package com.swrookie.bulletinboard.dto;

import java.sql.Timestamp; 

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
	private Long boardNo;
	private Long memberNo;
	private String title;								
	private String author;								
	private String content;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Integer count;
	
	@Builder
	public BoardDTO(Long boardNo, Long memberNo, String author, String title, String content,
					Timestamp createdDate, Timestamp modifiedDate)
	{
		this.boardNo = boardNo;
		this.memberNo = memberNo;
		this.author = author;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	
	public Board toEntity()
	{
		Board boardEntity = Board.builder()
								 .boardNo(boardNo)
								 .memberNo(memberNo)
								 .author(author)
								 .title(title)
								 .content(content)
								 .build();
		
		return boardEntity;
	}
	
	public Integer getCommentCount()
	{
		return count;
	}
}
