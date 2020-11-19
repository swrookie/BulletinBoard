package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name="comment")
public class Comment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentNo;			// Comment Number
	@Column(name="board_no")
	private Long boardNo;			// Post(Parent) number that contains comment(child) 
	private String author;			// Comment Author
	private String content;			// Comment content
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during create
	private Integer commentGroup;		// Outermost parent comment representing the whole depth
	private Long commentParent;		// Immediate parent comment of the child comment
	private Integer commentDepth;	// Depth of the child comment
	private Integer commentOrder;	// Order of the comment
	
	public Comment()
	{
	}
	
	@Builder
	public Comment(Long commentNo, Long boardNo, String author, String content, Timestamp createDate, 
				   Integer commentGroup, Long commentParent, Integer commentDepth, Integer commentOrder)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.commentGroup = commentGroup;
		this.commentParent = commentParent;
		this.commentDepth = commentDepth;
		this.commentOrder = commentOrder;
	}
}
