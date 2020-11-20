package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="comment")
@NoArgsConstructor
@ToString
public class Comment 
{
	@Id
	@GeneratedValue
	private Long commentNo;			// Comment Number
	@Column(name="board_no")
	private Long boardNo;			// Post(Parent) number that contains comment(child) 
	private String author;			// Comment Author
	@Lob
	private String content;			// Comment content
	@CreationTimestamp
	private Timestamp createDate;	// LocalDateTime during create
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_comment_no")
	private Comment parentComment;
	@OneToMany(mappedBy="parentComment", cascade=CascadeType.ALL)
	private List<Comment> childComment = new ArrayList<Comment>();
	private Integer commentDepth;
	
	@Builder
	public Comment(Long commentNo, Long boardNo, String author, String content, Timestamp createDate,
				   Comment parentComment, List<Comment> childComment, Integer commentDepth)
	{
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.author = author;
		this.content = content;
		this.createDate = createDate;
		this.parentComment = parentComment;
		this.childComment = childComment;
		this.commentDepth = commentDepth;
	}
}
