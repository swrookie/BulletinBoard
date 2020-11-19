package com.swrookie.bulletinboard.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.swrookie.bulletinboard.dto.BoardDTO;
import com.swrookie.bulletinboard.dto.FileDTO;
import com.swrookie.bulletinboard.entity.Board;
import com.swrookie.bulletinboard.security.MD5Generator;
import com.swrookie.bulletinboard.service.BoardService;
import com.swrookie.bulletinboard.service.CommentService;
import com.swrookie.bulletinboard.service.FileService;

@Controller
public class BoardController 
{
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FileService fileService;
	
	
	public BoardController(BoardService boardService, CommentService commentService, FileService fileService)
	{
		this.boardService = boardService;
		this.commentService = commentService;
		this.fileService = fileService;
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
	public String createPost(BoardDTO boardDto, List<MultipartFile> files)
	{
		boardService.createPost(boardDto);
		
		try
		{
			for (MultipartFile file : files)
			{
				String origFileName = file.getOriginalFilename();
				if (origFileName.equals(""))
					continue;
				System.out.println("Original file name: " + origFileName);
				String fileName = new MD5Generator(origFileName).toString();
				String savePath = System.getProperty("user.dir") + "\\files";
				
				if (!new File(savePath).exists())
				{
					try
					{
						new File(savePath).mkdir();
					}
					catch(Exception e)
					{
						e.getStackTrace();
					}
				}
				
				String filePath = savePath + "\\" + fileName;
				file.transferTo(new File(filePath));
				
				FileDTO fileDto = FileDTO.builder()
										 .boardNo(boardService.getRecentBoardNo())
										 .origFileName(origFileName)
										 .fileName(fileName)
										 .filePath(filePath)
										 .build();
				
				 fileService.createFile(fileDto);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	// View details of the post by clicking link on the title
	@GetMapping("/go_home/go_detail/{boardNo}")
	public String detailPost(@PathVariable("boardNo") Long boardNo, 
							 @PathVariable("boardNo") Board boardNum, Model model)
	{
		model.addAttribute("boardDto", boardService.updatePost(boardNo));
		model.addAttribute("fileList", fileService.readFile(boardNo));
		model.addAttribute("commentList", commentService.readComment(boardNum));
		
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
	@GetMapping("/delete_post/{boardNo}")
	public String delete(@PathVariable("boardNo")Long boardNo)
	{
		boardService.deletePost(boardNo);
		
		return "home_post_read";
	}
	
	// Search for posts after clicking search button
	@GetMapping("/go_home/search_posts")
	public String searchPost(@RequestParam(value="keyword") String keyword, Model model)
	{
		if (keyword.isEmpty())
			return "redirect:/";
		
		model.addAttribute("boardList", boardService.searchPost(keyword));
		
		return "home_post_read";
	}
}
