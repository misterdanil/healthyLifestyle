package org.healthylifestyle.filesystem.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageSavingRequest {
	private String relativePath;
	private MultipartFile file;

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
