package com.blog.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.AppConstant;
import com.blog.payload.UserDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public UserDto createUser(UserDto userDto) {
	User user = this.modelMapper.map(userDto, User.class);
	User created = this.userRepository.save(user);
		return this.modelMapper.map(created, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
	user.setEmail(userDto.getEmail());
	user.setAbout(userDto.getAbout());
	user.setName(userDto.getName());
	user.setPassword(userDto.getPassword());
	User updated = this.userRepository.save(user);
	return this.modelMapper.map(updated, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
	List<User> allUser = this.userRepository.findAll();
	List<UserDto> collect = allUser.stream().map((user)->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public UserDto getSingleUser(Integer userId) {
	User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		this.userRepository.delete(user);
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
	User user = this.modelMapper.map(userDto, User.class);
	user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
	Role role = this.roleRepository.findById(AppConstant.ROLE_NORMAL).get();
	user.getRoles().add(role);
	User register = this.userRepository.save(user);
	return this.modelMapper.map(register, UserDto.class);
	}

}
