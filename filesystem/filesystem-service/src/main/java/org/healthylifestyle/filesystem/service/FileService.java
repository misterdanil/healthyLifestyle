package org.healthylifestyle.filesystem.service;

import java.io.InputStream;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.filesystem.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	File save(MultipartFile file, String relativePath) throws ValidationException;

	File save(String filename, String file, String relativePath);
	
	InputStream findResourceByPath(String path);

	void remove(File file);
}
