package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category createdCate = this.categoryRepository.save(category);
		return this.modelMapper.map(createdCate, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer cateId) {
	Category category = this.categoryRepository.findById(cateId).orElseThrow(()->new ResourceNotFoundException("Category", "id", cateId));
	category.setDescription(categoryDto.getDescription());
	category.setTitle(categoryDto.getTitle());
	Category updated = this.categoryRepository.save(category);
	return this.modelMapper.map(updated, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategory = this.categoryRepository.findAll();
		List<CategoryDto> all = allCategory.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return all;
	}

	@Override
	public CategoryDto getSingleCategory(Integer cateId) {
	Category category = this.categoryRepository.findById(cateId).orElseThrow(()->new ResourceNotFoundException("Category", "id", cateId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
		Category category = this.categoryRepository.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "id", catId));
		this.categoryRepository.delete(category);
	}

}
