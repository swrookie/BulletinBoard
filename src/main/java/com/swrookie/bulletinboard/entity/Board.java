package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name="board")
@NoArgsConstructor
@ToString
public class Board 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boardNo;			// Post Number
	private String title;			// Post Title
	private String author;			// Post Author
	@Lob
	private String content;			// Post Content
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="board_no")
	private List<Comment> comments = new ArrayList<Comment>();
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="board_no")
	private List<File> files = new ArrayList<File>();
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during creation
	@UpdateTimestamp
	private Timestamp updateDate;	// LocalDateTime during update
	
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
