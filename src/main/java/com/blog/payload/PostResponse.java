package com.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	List<PostDto> content;
	Integer pageNumber;
	Integer pageSize;
	long totalElement;
	long totalPage;
	boolean isLastPage;
}
