package org.healthylifestyle.communication.common.dto.message;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class SaveMessageRequest {
	@NotBlank(message = "{message.save.text.notblank}")
	private String text;
	private List<Long> imageIds;
	private List<Long> videoIds;
	private List<Long> voiceIds;

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

}
