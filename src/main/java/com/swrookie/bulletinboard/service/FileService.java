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
	
	private FileDTO convertEntityToDto(File file)
	{
		return FileDTO.builder()
		   		  .fileNo(file.getFileNo())
		   		  .boardNo(file.getBoardNo())
		   		  .origFileName(file.getOrigFileName())
		   		  .fileName(file.getFileName())
		   		  .filePath(file.getFilePath())
				  .build();
	}
	
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
	
	@Transactional
	public FileDTO findByFileNo(Long fileNo)
	{
		File file = fileRepository.findById(fileNo).get();
		
        return FileDTO.builder()
     		   		  .fileNo(file.getFileNo())
     		   		  .boardNo(file.getBoardNo())
     		   		  .origFileName(file.getOrigFileName())
     		   		  .fileName(file.getFileName())
     		   		  .filePath(file.getFilePath())
     				  .build();
	}
	
	@Transactional
	public List<FileDTO> getFileDtoByBoardNo(Long boardNo)
	{
		List<FileDTO> fileDtoList = new ArrayList<FileDTO>();
		List<File> files = fileRepository.findByBoardNo(boardNo);
		for (File file : files)
		{
			fileDtoList.add(this.convertEntityToDto(file));
		}
		
		return fileDtoList;
	}
	
	@Transactional
	public void deleteFile(Long fileNo)
	{
		fileRepository.deleteById(fileNo);
	}
}
