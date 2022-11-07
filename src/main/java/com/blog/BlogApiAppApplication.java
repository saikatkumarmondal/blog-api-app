package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blog.entity.Role;
import com.blog.payload.AppConstant;
import com.blog.repository.RoleRepository;

@SpringBootApplication
public class BlogApiAppApplication implements CommandLineRunner{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BlogApiAppApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.bCryptPasswordEncoder.encode("1234"));
		try {
			Role adminRole = new Role(AppConstant.ROLE_ADMIN, "ROLE_ADMIN");
			
			Role normalRole = new Role(AppConstant.ROLE_NORMAL, "ROLE_ADMIN");
		List<Role> roles = List.of(adminRole,normalRole);
		List<Role> result = this.roleRepository.saveAll(roles);
		result.forEach((r)->{
			System.out.println(r.getName());
		});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
