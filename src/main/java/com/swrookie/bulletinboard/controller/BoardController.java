package com.swrookie.bulletinboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.swrookie.bulletinboard.dto.BoardDTO;
import com.swrookie.bulletinboard.service.BoardService;
import com.swrookie.bulletinboard.service.CommentService;

@Controller
public class BoardController 
{
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	
	public BoardController(BoardService boardService)
	{
		this.boardService = boardService;
	}
	
	// Go to home page and list posts when starting Spring Boot Application
	@GetMapping("/")
	public String readPost(@PageableDefault (size = 7) Pageable pageable, Model model)
	{
		model.addAttribute("boardList", boardService.readPost(pageable));
		model.addAttribute("startPage", BoardService.getStartPage());
		model.addAttribute("endPage", BoardService.getEndPage());
		model.addAttribute("currentPage", BoardService.getCurrentPage());
		model.addAttribute("lastPage", BoardService.getLastPage());
		
		return "home_post_read";
	}
	
	// Transfer to post creation page from home page
	@GetMapping("/create_post")
	public String createPost()
	{
		return "home_post_create";
	}
	
	// Create post by clicking write button and return to home page
	@PostMapping("/do_create")
	public String createPost(BoardDTO boardDto)
	{
		boardService.createPost(boardDto);
		
		return "redirect:/";
	}
	
	// View details of the post by clicking link on the title
	@GetMapping("/go_home/go_detail/{boardNo}")
	public String detailPost(@PathVariable("boardNo") Long boardNo, Model model)
	{
		model.addAttribute("boardDto", boardService.updatePost(boardNo));
		model.addAttribute("commentList", commentService.readComment(boardNo));
		return "home_post_detail";
	}
	
	// Transfer to post editing page from post detail page by clicking editing button
	@GetMapping("/go_home/go_detail/go_update/{boardNo}")
	public String editPost(@PathVariable("boardNo") Long boardNo, Model model)
	{
		model.addAttribute("boardDto", boardService.updatePost(boardNo));
		
		return "home_post_update";
	}
	
	// Update post by clicking update button and return to home page
	@PostMapping("/do_update")
	public String updatePost(BoardDTO boardDto, Model model)
	{
		boardService.createPost(boardDto);
		
		return "redirect:/go_home";
	}
	
	// Delete the post by clicking delete button and return to home page
	@GetMapping("/go_home/go_detail/delete_post/{boardNo}")
	public String delete(@PathVariable("boardNo")Long boardNo, Model model)
	{
		boardService.deletePost(boardNo);
		
		return "home_post_read";
	}
	
	// Search for posts after clicking search button
	@GetMapping("/go_home/search_posts")
	public String searchPost(@RequestParam(value = "keyword") String keyword, Model model)
	{
		if (keyword.isEmpty())
			return "redirect:/";
		
		model.addAttribute("boardList", boardService.searchPost(keyword));
		
		return "home_post_read";
	}
}
