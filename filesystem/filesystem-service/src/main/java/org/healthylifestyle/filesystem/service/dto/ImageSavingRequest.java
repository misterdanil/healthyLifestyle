package org.healthylifestyle.filesystem.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageSavingRequest {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
