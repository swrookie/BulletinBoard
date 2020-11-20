package com.swrookie.bulletinboard.service;

import java.util.ArrayList; 
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.swrookie.bulletinboard.dto.CommentDTO;
import com.swrookie.bulletinboard.entity.Comment;
import com.swrookie.bulletinboard.repository.CommentRepository;

@Service
public class CommentService 
{
	
//	https://javannspring.tistory.com/68?category=547345
	@Autowired
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository)
	{
		this.commentRepository = commentRepository;
	}
	
	private CommentDTO convertEntityToDto(Comment comment)
	{	
		return CommentDTO.builder()
						 .commentNo(comment.getCommentNo())
				         .boardNo(comment.getBoardNo())
				         .author(comment.getAuthor())
				         .content(comment.getContent())
				         .createDate(comment.getCreateDate())
				         .parentComment(comment.getParentComment())
				         .childComment(comment.getChildComment())
				         .commentDepth(comment.getCommentDepth())
				         .build();
	}
	
	@Transactional
	public void createComment(Comment comment)
	{
//		if (commentDto.getCommentNo() == null)
//			commentDto.setCommentDepth(1);
//		else
//		{
//			System.out.println("Adding child comment to parent comment");
//			Long parentCommentNo = commentDto.getCommentNo();
//			System.out.println("Parent comment no: " + parentCommentNo);
//			Comment parentComment = commentRepository.findById(parentCommentNo).get();
//			System.out.println("Parent info: " + parentComment.toString());
//			commentDto.setCommentDepth(parentComment.getCommentDepth() + 1);
//			parentComment.getChildComment().add(commentDto.toEntity());
////			commentDto.setParentComment(parentComment);
//			
//		}
//		commentRepository.save(commentDto.toEntity());
		
		Comment newComment = new Comment();

        if(comment.getCommentNo()== null){
            newComment.setCommentDepth(1);
        }

        else{
            Comment supComment = commentRepository.findById(comment.getCommentNo()).get();

            newComment.setCommentDepth(supComment.getCommentDepth() + 1);
            newComment.setParentComment(supComment);
            supComment.getChildComment().add(newComment);
        }
        
        newComment.setBoardNo(comment.getBoardNo());
        newComment.setContent(comment.getContent());

        commentRepository.save(newComment);
	}
	
	@Transactional
	public List<CommentDTO> readComment(Long boardNo)
	{
		List<Comment> comments = commentRepository.findByBoardNoOrderByCommentNoAscCommentDepthAsc(boardNo);
		List<CommentDTO> commentDtoList = new ArrayList<CommentDTO>();
		
		for (Comment comment : comments)
		{
			commentDtoList.add(this.convertEntityToDto(comment));
		}
		
		return commentDtoList;
	}

	
//	@Transactional
//	public CommentDTO updateComment(Long commentNo)
//	{
//		Comment comment = commentRepository.findById(commentNo).get();
//		
//		CommentDTO commentDto = CommentDTO.builder()
//										  .content(comment.getContent())
//										  .createDate(comment.getCreateDate())
//										  .build();
//		
//		return commentDto;
//	}
	
	@Transactional
	public void deleteComment(Long commentNo)
	{
		commentRepository.deleteById(commentNo);
	}
}
