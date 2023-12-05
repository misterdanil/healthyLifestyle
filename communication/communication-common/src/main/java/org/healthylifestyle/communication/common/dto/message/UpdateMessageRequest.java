package org.healthylifestyle.communication.common.dto.message;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class UpdateMessageRequest {
	@NotBlank(message = "message.save.text.notblank")
	private String text;
	private List<Long> imageIds;
	private List<Long> videoIds;
	private List<Long> voiceIds;
	private List<Long> deletedImageIds;
	private List<Long> deletedVideoIds;
	private List<Long> deletedVoiceIds;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public List<Long> getVoiceIds() {
		return voiceIds;
	}

	public void setVoiceIds(List<Long> voiceIds) {
		this.voiceIds = voiceIds;
	}

	public List<Long> getDeletedImageIds() {
		return deletedImageIds;
	}

	public void setDeletedImageIds(List<Long> deletedImageIds) {
		this.deletedImageIds = deletedImageIds;
	}

	public List<Long> getDeletedVideoIds() {
		return deletedVideoIds;
	}

	public void setDeletedVideoIds(List<Long> deletedVideoIds) {
		this.deletedVideoIds = deletedVideoIds;
	}

	public List<Long> getDeletedVoiceIds() {
		return deletedVoiceIds;
	}

	public void setDeletedVoiceIds(List<Long> deletedVoiceIds) {
		this.deletedVoiceIds = deletedVoiceIds;
	}

}
