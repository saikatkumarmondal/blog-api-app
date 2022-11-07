package com.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Integer id;
	@NotEmpty
	@Size(min = 3,message = "Name must be min of 4 characters")
	private String name;
	@Email
	private String email;
	@NotEmpty
	@Size(min = 3,message = "Name must be min of 4 characters")
	private String password;

	private String about;
}
