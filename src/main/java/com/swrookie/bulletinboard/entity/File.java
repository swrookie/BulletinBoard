package com.swrookie.bulletinboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="file")
@NoArgsConstructor
public class File 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fileNo;
	@Column(name="board_no")
	private Long boardNo;
	@Column(nullable=false)
	private String origFileName;
	@Column(nullable=false)
	private String fileName;
	@Column(nullable=false)
	private String filePath;
	
	@Builder
	public File(Long fileNo, Long boardNo, String origFileName, String fileName, String filePath)
	{
		this.fileNo = fileNo;
		this.boardNo = boardNo;
		this.origFileName = origFileName;
		this.fileName = fileName;
		this.filePath = filePath;
	}
}
