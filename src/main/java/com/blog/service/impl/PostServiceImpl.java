package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Pageable;
@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		Post postCreated = this.postRepository.save(post);

		return this.modelMapper.map(postCreated, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//post.setAddedDate(postDto.getAddedDate());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
	
		Pageable pageable	=PageRequest.of(pageNumber, pageSize, sort);
	
		Page<Post> findAll = this.postRepository.findAll(pageable);
		List<Post> allPost = findAll.getContent();
		List<PostDto> postDto = allPost.stream().map((p)->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setLastPage(findAll.isLast());
		postResponse.setPageNumber(findAll.getNumber());
		postResponse.setPageSize(findAll.getSize());
		postResponse.setTotalElement(findAll.getTotalElements());
		postResponse.setTotalPage(findAll.getTotalPages());
		
		return postResponse;
	}

	@Override
	public PostDto getSinglePost(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Page<Post> allPosts = this.postRepository.findByUser(user, pageable);
		List<PostDto> postDto = allPosts.stream().map((u) -> this.modelMapper.map(u, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setLastPage(allPosts.isLast());
		postResponse.setPageNumber(allPosts.getNumber());
		postResponse.setPageSize(allPosts.getSize());
		postResponse.setTotalElement(allPosts.getTotalElements());
		postResponse.setTotalPage(allPosts.getTotalPages());
		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Integer catId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		sort=Sort.by(sortBy).descending();
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Category category = this.categoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		Page<Post> allPost = this.postRepository.findByCategory(category, pageable);
		List<PostDto> postDto = allPost.stream().map((c) -> this.modelMapper.map(c, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setLastPage(allPost.isLast());
		postResponse.setPageNumber(allPost.getNumber());
		postResponse.setPageSize(allPost.getSize());
		postResponse.setTotalElement(allPost.getTotalElements());
		postResponse.setTotalPage(allPost.getTotalPages());
		return postResponse;
	}

	@Override
	public void deletePostId(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		this.postRepository.delete(post);

	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> findByTitleContaining = this.postRepository.searchByTitle("%"+keyword+"%");
		List<PostDto> collect = findByTitleContaining.stream().map((p) -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		return collect;
	}

}
