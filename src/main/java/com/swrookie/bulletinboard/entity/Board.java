package com.swrookie.bulletinboard.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="board")
@NoArgsConstructor

public class Board extends BaseTime
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boardNo;			// Post Number
	private String author;			// Post Author
	private String title;			// Post Title
	@Lob
	private String content;			// Post Content
	@OneToMany(mappedBy="boardNo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Comment> comments = new ArrayList<Comment>();
	@OneToMany(mappedBy="boardNo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<File> files = new ArrayList<File>();

	@Builder
	public Board(Long boardNo,  String title, String author, String content)
	{
		this.boardNo = boardNo;
		this.author = author;
		this.title = title;
		this.content = content;
	}
}
