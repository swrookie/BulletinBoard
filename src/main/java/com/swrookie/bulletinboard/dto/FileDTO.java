package com.swrookie.bulletinboard.dto;

import com.swrookie.bulletinboard.entity.File;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor()
public class FileDTO 
{
	private Long fileNo;
	private Long boardNo;
	private String origFileName;
	private String fileName;
	private String filePath;
	
	@Builder
	public FileDTO(Long fileNo, Long boardNo, String origFileName, String fileName, String filePath)
	{
		this.fileNo = fileNo;
		this.boardNo = boardNo;
		this.origFileName = origFileName;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public File toEntity()
	{
		File file = File.builder()
						.fileNo(fileNo)
						.boardNo(boardNo)
						.origFileName(origFileName)
						.fileName(fileName)
						.filePath(filePath)
						.build();
		
		return file;
	}
}
