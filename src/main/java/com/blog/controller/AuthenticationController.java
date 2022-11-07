package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exception.ApiException;
import com.blog.payload.UserDto;
import com.blog.security.JwtRequest;
import com.blog.security.JwtResponse;
import com.blog.security.JwtUtilHelper;
import com.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	@Autowired
	private UserService userService;
	//login
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
		this.authenticate(jwtRequest.getEmail(),jwtRequest.getPassword());
	UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
	String generateToken = this.jwtUtilHelper.generateToken(userDetails);
	return new ResponseEntity<JwtResponse>(new JwtResponse(generateToken),HttpStatus.OK);
	}

	private void authenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid username or password");
			throw new ApiException("Invalid username or password");
		}
		
	}
	
	//register
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registerUser = this.userService.registerUser(userDto);
	return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
}
