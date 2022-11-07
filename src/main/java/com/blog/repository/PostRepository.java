package com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	
	Page<Post> findByCategory(Category category,Pageable p);
	
	Page<Post> findByUser(User user,Pageable pageable);
	
	List<Post> findByTitleContaining(String keyword);
	
	@Query("select p from Post p where p.title LIKE :title")
	List<Post> searchByTitle(@Param("title") String title);
}
