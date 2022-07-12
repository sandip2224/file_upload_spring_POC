package com.example.uploadpoc_db.service;

import com.example.uploadpoc_db.model.File;
import com.example.uploadpoc_db.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl {
	@Autowired
	private FileRepository fileRepository;

	public File store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File FileDB = new File(fileName, file.getContentType(), file.getBytes());
		return fileRepository.save(FileDB);
	}
	public File getFile(String id) {
		return fileRepository.findById(id).get();
	}

	public Stream<File> getAllFiles() {
		return fileRepository.findAll().stream();
	}
}
