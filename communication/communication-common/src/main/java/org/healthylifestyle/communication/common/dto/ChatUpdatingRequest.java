package org.healthylifestyle.communication.common.dto;

import java.util.List;

import org.healthylifestyle.communication.model.settings.Invitation;
import org.healthylifestyle.communication.model.settings.Modification;
import org.healthylifestyle.communication.model.settings.Privacy;

import jakarta.validation.constraints.NotBlank;

public class ChatUpdatingRequest {
	@NotBlank(message = "Новое название не может быть пустым")
	private String title;
	private Invitation invitation;
	private Modification modification;
	private Privacy privacy;
	private List<Long> adminCandidateIds;
	private Long imageId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public List<Long> getAdminCandidateIds() {
		return adminCandidateIds;
	}

	public void setAdminCandidateIds(List<Long> adminCandidateIds) {
		this.adminCandidateIds = adminCandidateIds;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
