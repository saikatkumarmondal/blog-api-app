package com.blog.service;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface CategoryService {
	
	
	//create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer cateId);
	
	//get all category
	List<CategoryDto> getAllCategory();
	
	//get single category
	CategoryDto getSingleCategory(Integer cateId);
	
	//delete category
	void deleteCategory(Integer catId);
}
