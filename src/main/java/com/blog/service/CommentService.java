package com.blog.service;

import com.blog.payload.CommentDto;

public interface CommentService {
	
	//create comment
	CommentDto createComment(CommentDto commentDto,Integer userId,Integer postId);
	
	//delete comment
	void deleteComment(Integer commentId);
}
