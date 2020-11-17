package com.swrookie.bulletinboard.service;

import java.util.*;  

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.BoardDTO;
import com.swrookie.bulletinboard.entity.Board;
import com.swrookie.bulletinboard.repository.BoardRepository;

@Service
public class BoardService 
{
	@Autowired
	private BoardRepository boardRepository;
	
	private static final int PAGE_BLOCK_COUNT = 5;		// Max number of pages visible on block
	private static int startPage = 0;
	private static int endPage = 0;
	private static int currentPage = 0;
	private static int lastPage = 0;
	
	private BoardDTO convertEntityToDto(Board board)
	{
		return BoardDTO.builder()
					   .boardNo(board.getBoardNo())
					   .author(board.getAuthor())
					   .title(board.getTitle())
					   .createDate(board.getCreateDate())
					   .updateDate(board.getUpdateDate())
					   .build();
	}
	
	public BoardService(BoardRepository boardRepository)
	{
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public void createPost(BoardDTO boardDto)
	{
		boardRepository.save(boardDto.toEntity());
	}
	
	@Transactional
	public List<BoardDTO> readPost(Pageable pageable)
	{		
		Page<Board> page = boardRepository.findAll(pageable);
		currentPage = page.getPageable().getPageNumber();
		lastPage = page.getTotalPages();
		startPage = (currentPage / PAGE_BLOCK_COUNT) * PAGE_BLOCK_COUNT;
		endPage = (lastPage <= PAGE_BLOCK_COUNT) ? ((this.getBoardCount() < 1) ? lastPage : lastPage - 1) :
				  (lastPage - PAGE_BLOCK_COUNT > startPage) ? 
				   startPage + PAGE_BLOCK_COUNT - 1 : lastPage - 1; 
		
//		System.out.println("Current page number in terms of html: " + page.getPageable().getPageNumber());
//		System.out.println("Total Pages: " + page.getTotalPages());
//		System.out.println("Start page of the block: " + startPage);
//		System.out.println("End page of the block: " + endPage);
//		System.out.println("Last page of the total pages: " + lastPage);
//		System.out.println("page of Page<Board> data type: " + page.toString());
		
		List<Board> boards = page.getContent();
		List<BoardDTO> boardDtoList = new ArrayList<BoardDTO>();
		
		for (Board board : boards)
		{
			boardDtoList.add(this.convertEntityToDto(board));
		}
		
		return boardDtoList;
	}
	
	@Transactional
	public BoardDTO updatePost(Long boardNo)
	{
		Optional<Board> boardWrapper = boardRepository.findById(boardNo);
		Board board = boardWrapper.get();
		
		BoardDTO boardDto = BoardDTO.builder()
				.boardNo(board.getBoardNo())
				.author(board.getAuthor())
				.title(board.getTitle())
				.content(board.getContent())
				.createDate(board.getCreateDate())
				.updateDate(board.getUpdateDate())
				.build();
		
		return boardDto;
	}
	
	@Transactional
	public void deletePost(Long boardNo)
	{
		boardRepository.deleteById(boardNo);
	}
	
	@Transactional
	public List<BoardDTO> searchPost(String keyword)
	{
		List<Board> boards = boardRepository.findByTitleContaining(keyword);
		List<BoardDTO> boardDtoList = new ArrayList<BoardDTO>();
		
		System.out.println(boards.toString());
		
		if (boards.isEmpty())
			return boardDtoList;
		
		for (Board board : boards)
		{
			boardDtoList.add(this.convertEntityToDto(board));
		}
		
		return boardDtoList;
	}
	
	
	@Transactional
	public Long getBoardCount()
	{
		return boardRepository.count();
	}
	
	public static int getCurrentPage()
	{
		return currentPage;
	}
	
	public static int getStartPage()
	{
		return startPage;
	}
	
	public static int getEndPage()
	{
		return endPage;
	}
	
	public static int getLastPage()
	{
		return lastPage;
	}
}
