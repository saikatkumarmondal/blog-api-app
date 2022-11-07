package com.blog.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	
	//create comment
	@PostMapping("/user/{userId}/posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,@PathVariable("userId")Integer userId,@PathVariable("postId")Integer postId){
		CommentDto createComment = this.commentService.createComment(commentDto, userId, postId);
	return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId")Integer commentId){
		this.commentService.deleteComment(commentId);
		ApiResponse apiResponse = new ApiResponse("Comment Deleted..!!", true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NO_CONTENT);
	}
	
}
