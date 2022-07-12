package com.sandipan.uploadpoc_local.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl{
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageServiceImpl(Environment env) {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!!");
		}
	}

	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Could not save file. Error: " + e.getMessage());
		}
	}

	public Resource load(String filename){
		try {
			Path file = fileStorageLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(fileStorageLocation.toFile());
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.fileStorageLocation, 1).filter(path -> !path.equals(this.fileStorageLocation)).map(this.fileStorageLocation::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
}
