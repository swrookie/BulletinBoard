package com.swrookie.bulletinboard.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="comment")
@NoArgsConstructor
public class Comment extends BaseTime
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long commentNo;			// Comment Number (Primary Key)
	private Long boardNo;			// Board Number (Foreign Key)
	private String author;			// Comment Author
	@Lob
	private String content;			// Comment content
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent", updatable=false, nullable=true)
	private Comment parent;			// Post(Parent) number that contains comment(child) 
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	private List<Comment> children = new ArrayList<Comment>();
	private Integer depth;
	
	@Builder
	public Comment(Long commentNo, Long boardNo, String author, String content, Comment parent, Integer depth)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.parent = parent;
		this.depth = depth;
	}
}
