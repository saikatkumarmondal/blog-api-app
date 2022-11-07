package com.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.service.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		String filePath=path + File.separator+newFileName;
		File file2 = new File(path);
		if(!file2.exists()) {
			file2.mkdir();
		}
		
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return newFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String filePath=path + File.separator+fileName;
		InputStream inputStream = new FileInputStream(filePath);
		return inputStream;
	}

}
