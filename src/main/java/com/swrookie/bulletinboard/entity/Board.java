package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp; 

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name="board")
public class Board 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long boardNo;
	private String title;
	private String author;
	private String content;
	@CreationTimestamp
	private Timestamp createDate;		// LocalDateTime
	@UpdateTimestamp
	private Timestamp updateDate;	// LocalDateTime
	
	public Board()
	{
	}
	
	@Builder
	public Board(Long boardNo,  String title, String author, String content, Timestamp createDate)
	{
		this.boardNo = boardNo;
		this.author = author;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
	}
}
