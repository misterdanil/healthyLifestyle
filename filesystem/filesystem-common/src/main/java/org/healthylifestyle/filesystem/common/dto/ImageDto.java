package org.healthylifestyle.filesystem.common.dto;

public class ImageDto {
	private Long id;
	private FileDto file;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileDto getFile() {
		return file;
	}

	public void setFile(FileDto file) {
		this.file = file;
	}

}
