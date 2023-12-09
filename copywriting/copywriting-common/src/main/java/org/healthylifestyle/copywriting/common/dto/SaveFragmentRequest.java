package org.healthylifestyle.copywriting.common.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class SaveFragmentRequest {
	@NotNull
	private String uuid;
	@NotNull
	private Long htmlId;
	private Long cssId;
	private List<Long> imageIds;
	private List<Long> videoIds;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getHtmlId() {
		return htmlId;
	}

	public void setHtmlId(Long htmlId) {
		this.htmlId = htmlId;
	}

	public Long getCssId() {
		return cssId;
	}

	public void setCssId(Long cssId) {
		this.cssId = cssId;
	}

	public List<Long> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<Long> imageIds) {
		this.imageIds = imageIds;
	}

	public List<Long> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<Long> videoIds) {
		this.videoIds = videoIds;
	}

}
