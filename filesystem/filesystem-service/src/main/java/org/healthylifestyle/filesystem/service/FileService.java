package org.healthylifestyle.filesystem.service;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.filesystem.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	File save(MultipartFile file, String relativePath) throws ValidationException;
	
	void remove(File file);
}
