package com.swrookie.bulletinboard.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;   

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
//import org.lionsoul.jcseg.extractor.impl.TextRankKeywordsExtractor;
import org.lionsoul.jcseg.extractor.impl.TextRankSummaryExtractor;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.lionsoul.jcseg.sentence.SentenceSeg;
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
	private static int startPage = 0;					// Starting page number of the block
	private static int endPage = 0;						// Ending page number of the block
	private static int currentPage = 0;					// Page number of the current list
	private static int lastPage = 0;					// Last page of the total pages
	
	private BoardDTO convertEntityToDto(Board board)
	{
		return BoardDTO.builder()
					   .boardNo(board.getBoardNo())
					   .author(board.getAuthor())
					   .title(board.getTitle())
					   .createdDate(board.getCreatedDate())
					   .modifiedDate(board.getModifiedDate())
					   .build();
	}
	
	private void extractContent(BoardDTO boardDto) throws IOException
	{
		/*NLP Alternative API (HanLP): https://github.com/hankcs/HanLP
		  NLP API used (Jcseg): https://github.com/lionsoul2014/jcseg */
		
		String content = Jsoup.parse(boardDto.getContent()).text();
				
		SegmenterConfig config = new SegmenterConfig(true);
		config.setClearStopwords(true);							// Set filter stop words
		config.setAppendCJKSyn(false);							// Set close synonyms append
		config.setKeepUnregWords(false);						// Set remove unrecognized terms
		
		ADictionary dic = DictionaryFactory.createSingletonDictionary(config);
		ISegment seg = ISegment.COMPLEX.factory.create(config, dic);
		
		//TextRankKeywordsExtractor keywordsExtractor = new TextRankKeywordsExtractor(seg);
		TextRankSummaryExtractor keySentencesExtractor = new TextRankSummaryExtractor(seg, new SentenceSeg());

		//keySentencesExtractor.setSentenceNum(3);
		
		//List<String> contentKeywordList = keywordsExtractor.getKeywords(new StringReader(content));
		//List<String> contentSummaryList = keySentencesExtractor.getKeySentence(new StringReader(content));
		
		//String contentKeyword = String.join(", ", contentKeywordList);
		//String contentSummary = String.join("", contentSummaryList);
		//String contentKeyword = String.join(", ", HanLP.extractKeyword(content, 5));
		String contentSummary = keySentencesExtractor.getSummary(new StringReader(content), 1000);
				
		boardDto.setContentSummary(contentSummary);
		//boardDto.setContentKeyword(contentKeyword);
		
		System.out.println("Content after parsing html: " + content);
		System.out.println("Article summary: " + contentSummary);
	}
	
	// Determine page numbers for jsp view
	private void getPaging(Page<Board> page)
	{
		currentPage = page.getPageable().getPageNumber();
		lastPage = page.getTotalPages();
		startPage = (currentPage / PAGE_BLOCK_COUNT) * PAGE_BLOCK_COUNT;
		
		if (lastPage <= PAGE_BLOCK_COUNT)
		{
			if (lastPage == 0)
				endPage = lastPage;
			else
				endPage = lastPage - 1;
		}
		else
		{
			if (lastPage - PAGE_BLOCK_COUNT > startPage)
				endPage = startPage + PAGE_BLOCK_COUNT - 1;
			else
				endPage = lastPage - 1;
		}
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
		
		this.getPaging(page);
		
		List<Board> boards = page.getContent();
		List<BoardDTO> boardDtoList = new ArrayList<BoardDTO>();
		
		for (Board board : boards)
		{
			Integer count = board.getComments().size();
			BoardDTO boardDto = this.convertEntityToDto(board);
			boardDto.setCount(count);
			boardDtoList.add(boardDto);
		}
		
		return boardDtoList;
	}
	
	@Transactional
	public BoardDTO showPostDetail(Long boardNo) throws IOException
	{
		Board board =  boardRepository.findById(boardNo).get();
		BoardDTO boardDto = BoardDTO.builder()
									.boardNo(board.getBoardNo())
									.memberNo(board.getMemberNo())
									.author(board.getAuthor())
									.title(board.getTitle())
									.content(board.getContent())
									.build();
		
		this.extractContent(boardDto);
		
		return boardDto;
	}
	
	@Transactional
	public void deletePost(Long boardNo)
	{
		boardRepository.deleteById(boardNo);
	}
	
	@Transactional
	public List<BoardDTO> searchPost(String searchType, String keyword, Pageable pageable)
	{
		Page<Board> page = null;

		if (searchType.equals("Title"))
			page = boardRepository.findByTitleContaining(keyword, pageable);
		else if (searchType.equals("Content"))
			page = boardRepository.findByContentContaining(keyword, pageable);

		this.getPaging(page);
		
		List<Board> boards = page.getContent();
		List<BoardDTO> boardDtoList = new ArrayList<BoardDTO>();
		
		if (boards.isEmpty() || boards == null)
			return boardDtoList;
		
		for (Board board : boards)
		{
			Integer count = board.getComments().size();
			BoardDTO boardDto = this.convertEntityToDto(board);
			boardDto.setCount(count);
			boardDtoList.add(boardDto);
		}
		
		return boardDtoList;
	}
	
	@Transactional
	public Long getRecentBoardNo()
	{
		List<Board> boards = boardRepository.findAll();
		
		return boards.get(boards.size() - 1).getBoardNo();
	}
	
	@Transactional
	public Long getPostCount()
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
