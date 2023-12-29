package org.healthylifestyle.copywriting.common.dto;

import org.healthylifestyle.filesystem.common.dto.ImageDto;

public class ArticleDto {
	private Long id;
	private String title;
	private ImageDto image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ImageDto getImage() {
		return image;
	}

	public void setImage(ImageDto image) {
		this.image = image;
	}

}
