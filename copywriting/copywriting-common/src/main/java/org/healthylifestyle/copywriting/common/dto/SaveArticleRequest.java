package org.healthylifestyle.copywriting.common.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SaveArticleRequest {
	@NotNull
	private String uuid;
	@NotNull
	private String title;
	@NotEmpty
	private List<SaveFragmentRequest> fragmentRequests;
	@NotNull
	private Long imageId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SaveFragmentRequest> getFragmentRequests() {
		return fragmentRequests;
	}

	public void setFragmentRequests(List<SaveFragmentRequest> fragmentRequests) {
		this.fragmentRequests = fragmentRequests;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
