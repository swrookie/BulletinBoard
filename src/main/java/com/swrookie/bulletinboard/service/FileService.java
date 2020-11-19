package com.swrookie.bulletinboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.FileDTO;
import com.swrookie.bulletinboard.entity.File;
import com.swrookie.bulletinboard.repository.FileRepository;

@Service
public class FileService 
{
	@Autowired
	private FileRepository fileRepository;
	
	public FileService(FileRepository fileRepository)
	{
		this.fileRepository = fileRepository;
	}
	
	@Transactional
	public void createFile(FileDTO fileDto)
	{
		fileRepository.save(fileDto.toEntity());
	}
	
	@Transactional
	public List<FileDTO> readFile(Long boardNo)
	{
		List<File> files = fileRepository.findByBoardNo(boardNo);
		List<FileDTO> fileDtoList = new ArrayList<FileDTO>();
		
		for (File file : files)
		{
			fileDtoList.add(FileDTO.builder()
					   .fileNo(file.getFileNo())
					   .boardNo(boardNo)
					   .origFileName(file.getOrigFileName())
					   .fileName(file.getFileName())
					   .filePath(file.getFilePath())
					   .build());
		}
		
		return fileDtoList;
	}
}
