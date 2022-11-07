package com.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payload.ApiResponse;
import com.blog.payload.AppConstant;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	String path;
	//create post
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,@PathVariable("userId")Integer userId,@PathVariable("catId")Integer catId){
		PostDto createPost = this.postService.createPost(postDto, userId, catId);
	return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable("postId")Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//get single Post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId")Integer postId){
		PostDto post = this.postService.getSinglePost(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId")Integer postId){
		this.postService.deletePostId(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully..!!", true),HttpStatus.NO_CONTENT);
	}
	
	//get all Posts
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPost(
				@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
				@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
				
				){
			PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
		}
		
		//get post by user
		@GetMapping("/user/{userId}/posts")
		public ResponseEntity<PostResponse> getPostByUser(@PathVariable("userId")Integer userId,
		@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
		@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
		@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir,
		@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy
				){
			PostResponse allPost = this.postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
		}
		
		//get post by category
		@GetMapping("/category/{catId}/posts")
		public ResponseEntity<PostResponse> getPostByCategory(@PathVariable("catId")Integer catId,
				@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
				@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
				){
			PostResponse allPost = this.postService.getPostByCategory(catId, pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
		}
		
		//search post
		@GetMapping("/posts/search/{keyword}")
		public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyword")String keyword){
			List<PostDto> allPost = this.postService.searchPost(keyword);
			return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
		}
		
		
		
		//upload image
		
		@PostMapping("/posts/{postId}/upload/image")	
		public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile image,@PathVariable("postId")Integer postId){
			String uploadImage = this.fileService.uploadImage(path, image);
	PostDto post = this.postService.getSinglePost(postId);
	post.setImageName(uploadImage);
	PostDto updatePost = this.postService.updatePost(post, postId);
	return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
		}
		
		//get image
		@GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void getImage(@PathVariable("imageName")String imageName,HttpServletResponse res) throws IOException {
			
			InputStream resource = this.fileService.getResource(path, imageName);
			res.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy( resource,res.getOutputStream());
		
		}
}
