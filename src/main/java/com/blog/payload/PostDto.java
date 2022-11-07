package com.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Integer id;
	@NotEmpty
	@Size(min = 4,message = "Title must be min of 4 characters" )
	private String title;
	@NotEmpty
	@Size(min = 4,message = "Title must be min of 4 characters" )
	private String content;
	private Date addedDate;
	private String imageName;
	private UserDto userDto;
	private CategoryDto categoryDto;
	private Set<CommentDto> comments = new HashSet<>();
}
