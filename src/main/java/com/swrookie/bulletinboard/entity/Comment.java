package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="comment")
public class Comment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int commentNo;
	private int boardNo;
	private String author;
	private String content;
	private Timestamp createDate;
}
