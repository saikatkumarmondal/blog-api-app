package com.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {
	
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer catId);
	
	//update post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//update post
	PostDto getSinglePost(Integer postId);
	
	//get post by user
	
	PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get post by category
	PostResponse getPostByCategory(Integer catId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//delete post
	void deletePostId(Integer postId);
	
	//search post
	List<PostDto> searchPost(String keyword);
	
	
}
