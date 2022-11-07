package com.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.UserDto;
import com.blog.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	//create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	
	{
	UserDto createUser = this.userService.createUser(userDto);
	
	return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	
	}
	//update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId")Integer userId)
	{
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
	}
	
	
	//get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId")Integer userId)
	{
		UserDto updateUser = this.userService.getSingleUser(userId);
		
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
	}
	
	//get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		List<UserDto> user = this.userService.getAllUser();
		
		return new ResponseEntity<List<UserDto>>(user,HttpStatus.OK);
	}
	
	//delete single user
	//admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Integer userId)
	{
		this.userService.deleteUser(userId);
		ApiResponse apiResponse =new  ApiResponse("Deleted Successfully", true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
}


