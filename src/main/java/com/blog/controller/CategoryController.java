package com.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	// update category
	@PutMapping("/{cateId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("cateId") Integer cateId) {

		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, cateId);

		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	// get single category
	@GetMapping("/{cateId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("cateId") Integer cateId) {

		CategoryDto category = this.categoryService.getSingleCategory(cateId);

		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	// get all category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {

		List<CategoryDto> category = this.categoryService.getAllCategory();

		return new ResponseEntity<List<CategoryDto>>(category, HttpStatus.OK);
	}

	// delete category
	@DeleteMapping("/{cateId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("cateId") Integer cateId) {

		this.categoryService.deleteCategory(cateId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully Deleted..!!", false),
				HttpStatus.NOT_FOUND);
	}
}
