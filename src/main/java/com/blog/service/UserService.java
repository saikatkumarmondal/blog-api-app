package com.blog.service;

import java.util.List;

import com.blog.payload.UserDto;


public interface UserService {
	//create user
	UserDto createUser(UserDto userDto);
	//update user
	UserDto updateUser(UserDto userDto ,Integer userId);
	//get all user
	List<UserDto> getAllUser();
	//get single user
	UserDto getSingleUser(Integer userId);
	//delete user
	void deleteUser(Integer userId);
	
	
	//register user
	UserDto registerUser(UserDto userDto);
	
}
