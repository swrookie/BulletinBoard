package com.swrookie.bulletinboard.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.swrookie.bulletinboard.dto.BoardDTO;
import com.swrookie.bulletinboard.dto.FileDTO;
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
	
	private void createFile(List<MultipartFile> files)
	{
		try
		{
			if (files != null)
			{
				for (MultipartFile file : files)
				{
					String origFileName = file.getOriginalFilename();
					if (origFileName.equals(""))
						continue;
					
//					System.out.println("Original file name: " + origFileName);
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public BoardController(BoardService boardService, CommentService commentService, FileService fileService)
	{
		this.boardService = boardService;
		this.commentService = commentService;
		this.fileService = fileService;
	}
	
	// Go to home page and list posts when starting Spring Boot Application
	@GetMapping("/")
	public String readPost(@PageableDefault (size = 7, sort = "boardNo", direction = Sort.Direction.DESC) 
						   Pageable pageable, Model model)
	{
		model.addAttribute("boardList", boardService.readPost(pageable));
		model.addAttribute("startPage", BoardService.getStartPage());
		model.addAttribute("endPage", BoardService.getEndPage());
		model.addAttribute("currentPage", BoardService.getCurrentPage());
		model.addAttribute("lastPage", BoardService.getLastPage());
		
		return "home";
	}
	
	// Transfer to post creation page from home page
	@GetMapping("/post/write")
	public String createPost()
	{
		return "write";
	}
	
	// Create post by clicking write button and return to home page
	@PostMapping("/post/write")
	@ResponseBody
	public ResponseEntity<Integer> createPost(@RequestPart("boardDto") BoardDTO boardDto, 
											  @RequestPart("files") List<MultipartFile> files)
	{
		boardService.createPost(boardDto);
		this.createFile(files);
		
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	
	@GetMapping("post/{boardNo}/download/{fileNo}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileNo") Long fileNo) throws IOException
	{
		FileDTO fileDto = fileService.getFile(fileNo);
		Path path = Paths.get(fileDto.getFilePath());
		Resource resource = new InputStreamResource(Files.newInputStream(path));

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
							 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
							 + fileDto.getOrigFileName() + "\"").body(resource);                  
	}
	
	// View details of the post by clicking link on the title
	@GetMapping("/post/{boardNo}")
	public String detail(@PathVariable("boardNo") Long boardNo, Model model)
	{
		model.addAttribute("boardDto", boardService.showPostDetail(boardNo));
		model.addAttribute("fileList", fileService.readFile(boardNo));
		model.addAttribute("commentList", commentService.readComment(boardNo));
		
		return "detail";
	}
	
	// Transfer to post editing page from post detail page by clicking editing button
	@GetMapping("/post/{boardNo}/update")
	public String editPost(@PathVariable("boardNo") Long boardNo, Model model)
	{
		model.addAttribute("boardDto", boardService.showPostDetail(boardNo));
		
		return "update";
	}
	
	// Update post by clicking update button and return to home page
	@PutMapping("/post/{boardNo}/update")
	@ResponseBody
	public ResponseEntity<Integer> updatePost(@RequestBody BoardDTO boardDto)
	{
		boardService.createPost(boardDto);
		
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	
	// Delete the post by clicking delete button and return to home page
	@DeleteMapping("/post/{boardNo}")
	@ResponseBody
	public ResponseEntity<Integer> delete(@PathVariable("boardNo") Long boardNo)
	{
		boardService.deletePost(boardNo);
		
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	
	// Search for posts after clicking search button
	@GetMapping("/search")
	public String searchPost(@RequestParam(value="keyword", required=false) String keyword, 
							 @RequestParam(value="option") String searchOption, Model model)
	{
		if (keyword == null || keyword.isEmpty())
			return "redirect:/";
		
		model.addAttribute("boardList", boardService.searchPost(keyword));
		
		return "home";
	}
}
