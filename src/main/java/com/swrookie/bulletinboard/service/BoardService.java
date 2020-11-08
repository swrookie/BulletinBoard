package com.swrookie.bulletinboard.service;

import java.util.*; 

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.BoardDTO;
import com.swrookie.bulletinboard.entity.Board;
import com.swrookie.bulletinboard.repository.BoardRepository;

@Service
public class BoardService 
{
	private BoardRepository boardRepository;
//	private static final int PAGE_BLOCK_COUNT = 5;		// Max number of pages visible on block
//	private static final int PAGE_POST_COUNT = 5;		// Max number of posts visible on page
	
	private static int startPage = 0;
	private static int endPage = 0;
	
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
		boardRepository.save(boardDto.toEntity()).getBoardNo();
	}
	
	@Transactional
	public List<BoardDTO> readPost(@PageableDefault (size = 5) Pageable pageable)
	{
//		Page<Board> page = boardRepository.findAll(PageRequest.of(pageNum - 1,
//																  PAGE_POST_COUNT,
//																  Sort.by(Sort.Direction.DESC, "createDate")));
		
		Page<Board> page = boardRepository.findAll(pageable);
		startPage = Math.max(1,  page.getPageable().getPageNumber() - 5);
		endPage = Math.min(page.getTotalPages(), page.getPageable().getPageNumber() + 5);
		
		System.out.println("Start page: " + startPage);
		System.out.println("End page: " + endPage);
		System.out.println("page of Page<Board> data type: " + page.toString());
		System.out.println("getContent method fo page" + page.getContent());
		
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
	
//	public int[] getPageList(int curPageNum)
//	{
//		int[] pageList = new int[PAGE_BLOCK_COUNT];
//		
//		// Total Posts Count
//		Double postsTotalCount = Double.valueOf(this.getBoardCount());
//		System.out.println();
//		System.out.println("Current page number: " + curPageNum);
//		System.out.println("Total post count: " + postsTotalCount);
//		
//		// Last Page Number based on Total Posts Count
//		int totalLastPageNum = (int)(Math.ceil(postsTotalCount / PAGE_POST_COUNT));
//		System.out.println("Last page number based on total post count: " + totalLastPageNum);
//		
//		// Last Page Number based on current page
//		int blockLastPageNum = curPageNum <= PAGE_BLOCK_COUNT ? PAGE_BLOCK_COUNT : curPageNum + PAGE_BLOCK_COUNT - 1;
//							   
//		System.out.println("Last page number shown on block based on current page: " + blockLastPageNum);
//		
//		// Page Start Number
//		curPageNum = blockLastPageNum - PAGE_BLOCK_COUNT + 1;
//		
//		System.out.println("New starting page: " + curPageNum);
//		
//		// Give page numbers
//		for (int val = curPageNum, i = 0; val <= blockLastPageNum; val++, i++)
//		{
//			System.out.println("Page: " + val);
//			pageList[i] = val;
//			//pageList.add(val);
//		}
//		
//		return pageList;
//	}
	
	public static int getStartPage()
	{
		return startPage;
	}
	
	public static int getEndPage()
	{
		return endPage;
	}
}
